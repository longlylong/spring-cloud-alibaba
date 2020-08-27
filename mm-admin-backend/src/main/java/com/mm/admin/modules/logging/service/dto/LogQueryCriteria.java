package com.mm.admin.modules.logging.service.dto;

import com.mm.admin.common.annotation.TDQuery;
import com.thatday.common.model.RequestPostVo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 日志查询类
 */
@Data
public class LogQueryCriteria extends RequestPostVo {

    @TDQuery(blurry = "username,description,address,requestIp,method,params")
    private String blurry;

    @TDQuery
    private String logType;

    @TDQuery(type = TDQuery.Type.BETWEEN)
    private List<Timestamp> createTime;
}
