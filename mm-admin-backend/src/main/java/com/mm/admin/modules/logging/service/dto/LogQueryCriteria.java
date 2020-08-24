package com.mm.admin.modules.logging.service.dto;

import com.mm.admin.common.annotation.Query;
import com.thatday.common.model.RequestPostVo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 日志查询类
 */
@Data
public class LogQueryCriteria extends RequestPostVo {

    @Query(blurry = "username,description,address,requestIp,method,params")
    private String blurry;

    @Query
    private String logType;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
