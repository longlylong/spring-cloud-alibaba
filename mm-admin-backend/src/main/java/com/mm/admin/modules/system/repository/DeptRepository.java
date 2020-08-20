package com.mm.admin.modules.system.repository;

import com.mm.admin.modules.system.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface DeptRepository extends JpaRepository<Dept, Long>, JpaSpecificationExecutor<Dept> {

    /**
     * 根据 PID 查询
     */
    List<Dept> findByPid(Long id);

    /**
     * 获取顶级部门
     */
    List<Dept> findByPidIsNull();

    /**
     * 根据角色ID 查询
     */
    @Query(value = "select d.* from sys_dept d, sys_roles_depts r where " +
            "d.dept_id = r.dept_id and r.role_id = ?1", nativeQuery = true)
    Set<Dept> findByRoleId(Long roleId);

    /**
     * 判断是否存在子节点
     */
    int countByPid(Long pid);

    /**
     * 根据ID更新sub_count
     */
    @Modifying
    @Query(value = " update sys_dept set sub_count = ?1 where dept_id = ?2 ", nativeQuery = true)
    void updateSubCntById(Integer count, Long id);
}
