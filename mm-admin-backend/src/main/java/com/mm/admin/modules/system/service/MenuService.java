package com.mm.admin.modules.system.service;

import com.mm.admin.modules.system.domain.Menu;
import com.mm.admin.modules.system.service.dto.MenuDto;
import com.mm.admin.modules.system.service.dto.MenuQueryCriteria;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface MenuService {

    /**
     * 查询全部数据
     */
    List<MenuDto> queryAll(MenuQueryCriteria criteria, Boolean isQuery) throws Exception;

    /**
     * 根据ID查询
     */
    MenuDto findById(long id);

    /**
     * 创建
     */
    void create(Menu resources);

    /**
     * 编辑
     */
    void update(Menu resources);

    /**
     * 获取待删除的菜单
     */
    Set<Menu> getDeleteMenus(List<Menu> menuList, Set<Menu> menuSet);

    /**
     * 构建菜单树
     */
    List<MenuDto> buildTree(List<MenuDto> menuDtos);

    /**
     * 构建菜单树
     */
    Object buildMenus(List<MenuDto> menuDtos);

    /**
     * 根据ID查询
     */
    Menu findOne(Long id);

    /**
     * 删除
     */
    void delete(Set<Menu> menuSet);

    /**
     * 导出
     */
    void download(List<MenuDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 懒加载菜单数据
     */
    List<MenuDto> getMenus(Long pid);

    /**
     * 根据ID获取同级与上级数据
     */
    List<MenuDto> getSuperior(MenuDto menuDto, List<Menu> objects);

    /**
     * 根据当前用户获取菜单
     */
    List<MenuDto> findByUser(Long currentUserId);
}
