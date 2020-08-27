package com.mm.admin.modules.system.service.dto;

import com.mm.admin.common.annotation.TDQuery;
import lombok.Data;

/**
 * 公共查询类
 */
@Data
public class DictQueryCriteria {

    @TDQuery(blurry = "name,description")
    private String blurry;
}
