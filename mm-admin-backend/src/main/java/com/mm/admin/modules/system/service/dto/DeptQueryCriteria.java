package com.mm.admin.modules.system.service.dto;

import com.mm.admin.common.annotation.DataPermission;
import com.mm.admin.common.annotation.TDQuery;
import com.mm.admin.common.base.BaseRequestVo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@DataPermission(fieldName = "id")
public class DeptQueryCriteria extends BaseRequestVo {

    @TDQuery(type = TDQuery.Type.INNER_LIKE)
    private String name;

    @TDQuery
    private Boolean enabled;

    @TDQuery
    private Long pid;

    @TDQuery(type = TDQuery.Type.IS_NULL, propName = "pid")
    private Boolean pidIsNull;

    @TDQuery(type = TDQuery.Type.BETWEEN)
    private List<Timestamp> createTime;
}
