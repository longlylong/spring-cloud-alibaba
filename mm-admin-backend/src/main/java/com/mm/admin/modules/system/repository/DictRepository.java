package com.mm.admin.modules.system.repository;

import com.mm.admin.modules.system.domain.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Set;

public interface DictRepository extends JpaRepository<Dict, Long>, JpaSpecificationExecutor<Dict> {

    /**
     * 删除
     */
    void deleteByIdIn(Set<Long> ids);

    /**
     * 查询
     */
    List<Dict> findByIdIn(Set<Long> ids);
}
