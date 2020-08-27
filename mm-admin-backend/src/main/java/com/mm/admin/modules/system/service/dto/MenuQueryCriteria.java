package com.mm.admin.modules.system.service.dto;

import com.mm.admin.common.annotation.TDQuery;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 公共查询类
 */
@Data
public class MenuQueryCriteria {

    @TDQuery(blurry = "title,component,permission")
    private String blurry;

    @TDQuery(type = TDQuery.Type.BETWEEN)
    private List<Timestamp> createTime;

    @TDQuery(type = TDQuery.Type.IS_NULL, propName = "pid")
    private Boolean pidIsNull;

    @TDQuery
    private Long pid;
}
