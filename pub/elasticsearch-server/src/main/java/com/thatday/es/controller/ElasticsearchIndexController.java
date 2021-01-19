package com.thatday.es.controller;

import com.thatday.common.model.Result;
import com.thatday.es.common.pojo.IndexAndMappingEntity;
import com.thatday.es.service.ElasticsearchIndexService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 索引操作控制器
 */
@RestController
@RequestMapping("/indices")
@Log4j2
public class ElasticsearchIndexController {

    ElasticsearchIndexService elasticsearchIndexService;

    /**
     * 新增索引、映射
     * addIndex
     * 1.0.0
     */
    @PostMapping(value = "/addIndexAndMapping")
    public Result<Boolean> addIndexAndMapping(@Valid @RequestBody IndexAndMappingEntity entity) {
        try {
            boolean result = elasticsearchIndexService.addIndexAndMapping(entity);
            log.info("addIndexAndMapping>>>CommonEntity{}", entity);
            log.info("addIndexAndMapping>>>result:{}", result);
            return Result.buildSuccess(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
