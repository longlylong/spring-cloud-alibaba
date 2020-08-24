package com.mm.admin.modules.system.service;

import com.mm.admin.modules.system.domain.Role;
import com.mm.admin.modules.system.service.dto.RoleDto;
import com.mm.admin.modules.system.service.dto.RoleQueryCriteria;
import com.mm.admin.modules.system.service.dto.RoleSmallDto;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface RoleService {

    /**
     * 查询全部数据
     */
    List<RoleDto> queryAll();

    /**
     * 根据ID查询
     */
    RoleDto findById(long id);

    /**
     * 创建
     */
    void create(Role resources);

    /**
     * 编辑
     */
    void update(Role resources);

    /**
     * 删除
     */
    void delete(Set<Long> ids);

    /**
     * 根据用户ID查询
     */
    List<RoleSmallDto> findByUsersId(Long id);

    /**
     * 根据角色查询角色级别
     */
    Integer findByRoles(Set<Role> roles);

    /**
     * 修改绑定的菜单
     */
    void updateMenu(Role resources, RoleDto roleDTO);

    /**
     * 解绑菜单
     */
    void untiedMenu(Long id);

    /**
     * 待条件分页查询
     */
    Object queryAll(RoleQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部
     */
    List<RoleDto> queryAll(RoleQueryCriteria criteria);

    /**
     * 导出数据
     */
    void download(List<RoleDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 验证是否被用户关联
     */
    void verification(Set<Long> ids);

    /**
     * 根据菜单Id查询
     */
    List<Role> findInMenuId(List<Long> menuIds);
}
