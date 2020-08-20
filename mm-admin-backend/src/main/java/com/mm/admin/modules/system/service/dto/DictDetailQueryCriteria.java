package com.mm.admin.modules.system.service.dto;

import com.mm.admin.common.annotation.Query;
import lombok.Data;

@Data
public class DictDetailQueryCriteria {

    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    @Query(propName = "name", joinName = "dict")
    private String dictName;
}
