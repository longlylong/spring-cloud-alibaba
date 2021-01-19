package com.thatday.es.common.pojo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BulkEntity extends CommonEntity {

    //批量增加list
    private List<Map<String, Object>> list;

}
