package com.thatday.es.common.pojo;

import lombok.Data;

import java.util.Map;

@Data
public class MatchEntity extends CommonEntity {

    //动态查询参数封装
    Map<String, Object> map;

}
