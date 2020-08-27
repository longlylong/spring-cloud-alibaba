package com.mm.admin.modules.system.service.dto;

import com.mm.admin.common.annotation.TDQuery;
import lombok.Data;

@Data
public class DictDetailQueryCriteria {

    @TDQuery(type = TDQuery.Type.INNER_LIKE)
    private String label;

    @TDQuery(propName = "name", joinName = "dict")
    private String dictName;
}
