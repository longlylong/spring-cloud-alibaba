package com.thatday.controller;

import com.thatday.common.model.common.NextIdVo;
import com.thatday.tinyid.base.entity.SegmentId;
import com.thatday.tinyid.base.generator.IdGenerator;
import com.thatday.tinyid.base.service.SegmentIdService;
import com.thatday.tinyid.server.factory.impl.IdGeneratorFactoryServer;
import com.thatday.tinyid.server.service.TinyIdTokenService;
import com.thatday.tinyid.server.vo.ErrorCode;
import com.thatday.tinyid.server.vo.Response;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author du_imba
 */
@RestController
public class IdController {

    private static final Logger logger = LoggerFactory.getLogger(IdController.class);

    private final IdGeneratorFactoryServer idGeneratorFactoryServer;
    private final SegmentIdService segmentIdService;
    private final TinyIdTokenService tinyIdTokenService;

    @Value("${batch.size.max}")
    private Integer batchSizeMax;

    @Value("${server.port}")
    private int port;

    @Autowired
    public IdController(IdGeneratorFactoryServer idGeneratorFactoryServer, SegmentIdService segmentIdService, TinyIdTokenService tinyIdTokenService) {
        this.idGeneratorFactoryServer = idGeneratorFactoryServer;
        this.segmentIdService = segmentIdService;
        this.tinyIdTokenService = tinyIdTokenService;
    }

    @GetMapping("/getId")
    public String getId() {
        return port + "：/getId";
    }

    //返回格式化后的id前面补0的
    @PostMapping("/id/nextIdFormatSimple")
    public String nextIdFormatSimple(@RequestBody NextIdVo nextIdVo) {

        var id = nextIdSimple(nextIdVo.getBizType(), nextIdVo.getBatchSize(), nextIdVo.getToken());
        if (StringUtils.isEmpty(id)) {
            return "error";
        }

        var sb = new StringBuilder();
        for (int x = 0; x < nextIdVo.getFormatSize() - id.length(); x++) {
            sb.append("0");
        }
        sb.append(id);
        return sb.toString();
    }

    //json
    @PostMapping("/id/nextId")
    public Response<List<Long>> nextId(String bizType, Integer batchSize, String token) {
        Response<List<Long>> response = new Response<>();
        Integer newBatchSize = checkBatchSize(batchSize);
        if (!tinyIdTokenService.canVisit(bizType, token)) {
            response.setCode(ErrorCode.TOKEN_ERR.getCode());
            response.setMessage(ErrorCode.TOKEN_ERR.getMessage());
            return response;
        }
        try {
            IdGenerator idGenerator = idGeneratorFactoryServer.getIdGenerator(bizType);
            List<Long> ids = idGenerator.nextId(newBatchSize);
            response.setData(ids);
        } catch (Exception e) {
            response.setCode(ErrorCode.SYS_ERR.getCode());
            response.setMessage(e.getMessage());
            logger.error("nextId error", e);
        }
        return response;
    }

    //单个id
    @PostMapping("/id/nextIdSimple")
    public String nextIdSimple(String bizType, Integer batchSize, String token) {
        Integer newBatchSize = checkBatchSize(batchSize);
        if (!tinyIdTokenService.canVisit(bizType, token)) {
            return "";
        }
        String response = "";
        try {
            IdGenerator idGenerator = idGeneratorFactoryServer.getIdGenerator(bizType);
            if (newBatchSize == 1) {
                Long id = idGenerator.nextId();
                response = id + "";
            } else {
                List<Long> idList = idGenerator.nextId(newBatchSize);
                StringBuilder sb = new StringBuilder();
                for (Long id : idList) {
                    sb.append(id).append(",");
                }
                response = sb.deleteCharAt(sb.length() - 1).toString();
            }
        } catch (Exception e) {
            logger.error("nextIdSimple error", e);
        }
        return response;
    }

    //当前详细信息
    @PostMapping("/id/nextSegmentId")
    public Response<SegmentId> nextSegmentId(String bizType, String token) {
        Response<SegmentId> response = new Response<>();
        if (!tinyIdTokenService.canVisit(bizType, token)) {
            response.setCode(ErrorCode.TOKEN_ERR.getCode());
            response.setMessage(ErrorCode.TOKEN_ERR.getMessage());
            return response;
        }
        try {
            SegmentId segmentId = segmentIdService.getNextSegmentId(bizType);
            response.setData(segmentId);
        } catch (Exception e) {
            response.setCode(ErrorCode.SYS_ERR.getCode());
            response.setMessage(e.getMessage());
            logger.error("nextSegmentId error", e);
        }
        return response;
    }

    private Integer checkBatchSize(Integer batchSize) {
        Integer newBatchSize = batchSize;
        if (newBatchSize == null) {
            newBatchSize = 1;
        }
        if (newBatchSize > batchSizeMax) {
            newBatchSize = batchSizeMax;
        }
        return newBatchSize;
    }

}
