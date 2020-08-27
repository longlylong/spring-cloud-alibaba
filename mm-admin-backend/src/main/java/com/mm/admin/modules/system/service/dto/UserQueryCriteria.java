package com.mm.admin.modules.system.service.dto;

import com.mm.admin.common.annotation.TDQuery;
import com.thatday.common.model.RequestPostVo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserQueryCriteria extends RequestPostVo {

    @TDQuery
    private Long id;

    @TDQuery(propName = "id", type = TDQuery.Type.IN, joinName = "dept")
    private Set<Long> deptIds = new HashSet<>();

    @TDQuery(blurry = "email,username,nickName")
    private String blurry;

    @TDQuery
    private Boolean enabled;

    private Long deptId;

    @TDQuery(type = TDQuery.Type.BETWEEN)
    private List<Timestamp> createTime;
}
