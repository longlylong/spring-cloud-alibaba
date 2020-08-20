package com.mm.admin.modules.system.repository;

import com.mm.admin.modules.system.domain.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Set;

public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {

    /**
     * 根据名称查询
     */
    Job findByName(String name);

    /**
     * 根据Id删除
     */
    void deleteAllByIdIn(Set<Long> ids);
}
