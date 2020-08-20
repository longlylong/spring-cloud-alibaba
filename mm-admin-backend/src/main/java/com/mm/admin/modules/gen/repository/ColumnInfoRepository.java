package com.mm.admin.modules.gen.repository;

import com.mm.admin.modules.gen.domain.ColumnInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ColumnInfoRepository extends JpaRepository<ColumnInfo, Long> {

    /**
     * 查询表信息
     */
    List<ColumnInfo> findByTableNameOrderByIdAsc(String tableName);
}
