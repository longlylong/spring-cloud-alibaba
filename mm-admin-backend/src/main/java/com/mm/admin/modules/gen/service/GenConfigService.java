package com.mm.admin.modules.gen.service;


import com.mm.admin.modules.gen.domain.GenConfig;

public interface GenConfigService {

    /**
     * 查询表配置
     */
    GenConfig find(String tableName);

    /**
     * 更新表配置
     */
    GenConfig update(String tableName, GenConfig genConfig);
}
