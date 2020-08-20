package com.mm.admin.modules.gen.repository;

import com.mm.admin.modules.gen.domain.GenConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenConfigRepository extends JpaRepository<GenConfig, Long> {

    /**
     * 查询表配置
     */
    GenConfig findByTableName(String tableName);
}
