package com.thatday.es.service;


import com.thatday.es.common.pojo.CommonEntity;
import com.thatday.es.common.pojo.IndexAndMappingEntity;

/**
 * 索引操作接口
 */
public interface ElasticsearchIndexService {

    //新增索引+映射
    boolean addIndexAndMapping(IndexAndMappingEntity entity) throws Exception;

}
