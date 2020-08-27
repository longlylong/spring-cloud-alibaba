package com.mm.admin.modules.system.service.dto;

import com.mm.admin.common.annotation.TDQuery;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 公共查询类
 */
@Data
public class RoleQueryCriteria {

    @TDQuery(blurry = "name,description")
    private String blurry;

    @TDQuery(type = TDQuery.Type.BETWEEN)
    private List<Timestamp> createTime;
}
