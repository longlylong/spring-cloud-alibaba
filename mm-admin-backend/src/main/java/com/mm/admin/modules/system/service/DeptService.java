package com.mm.admin.modules.system.service;

import com.mm.admin.modules.system.domain.Dept;
import com.mm.admin.modules.system.service.dto.DeptDto;
import com.mm.admin.modules.system.service.dto.DeptQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface DeptService {

    /**
     * 查询所有数据
     */
    List<DeptDto> queryAll(DeptQueryCriteria criteria, Boolean isQuery) throws Exception;

    /**
     * 根据ID查询
     */
    DeptDto findById(Long id);

    /**
     * 创建
     */
    void create(Dept resources);

    /**
     * 编辑
     */
    void update(Dept resources);

    /**
     * 删除
     */
    void delete(Set<DeptDto> deptDtos);

    /**
     * 根据PID查询
     */
    List<Dept> findByPid(long pid);

    /**
     * 根据角色ID查询
     */
    Set<Dept> findByRoleId(Long id);

    /**
     * 导出数据
     */
    void download(List<DeptDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 获取待删除的部门
     */
    Set<DeptDto> getDeleteDepts(List<Dept> deptList, Set<DeptDto> deptDtos);

    /**
     * 根据ID获取同级与上级数据
     */
    List<DeptDto> getSuperior(DeptDto deptDto, List<Dept> depts);

    /**
     * 构建树形数据
     */
    Object buildTree(List<DeptDto> deptDtos);

    /**
     * 获取
     */
    List<Long> getDeptChildren(Long deptId, List<Dept> deptList);

    /**
     * 验证是否被角色或用户关联
     */
    void verification(Set<DeptDto> deptDtos);
}
