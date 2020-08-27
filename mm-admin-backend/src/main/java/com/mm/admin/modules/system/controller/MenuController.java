package com.mm.admin.modules.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.mm.admin.common.annotation.UserPermission;
import com.mm.admin.common.utils.PageUtil;
import com.mm.admin.modules.logging.annotation.Log;
import com.mm.admin.modules.system.domain.Menu;
import com.mm.admin.modules.system.service.MenuService;
import com.mm.admin.modules.system.service.dto.MenuDto;
import com.mm.admin.modules.system.service.dto.MenuQueryCriteria;
import com.mm.admin.modules.system.service.mapstruct.MenuMapper;
import com.thatday.common.exception.GlobalException;
import com.thatday.common.model.RequestPostVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
public class MenuController {

    private static final String ENTITY_NAME = "menu";
    private final MenuService menuService;
    private final MenuMapper menuMapper;

    @Log("导出菜单数据")
    @GetMapping(value = "/download")
    @UserPermission("menu:list")
    public void download(HttpServletResponse response, MenuQueryCriteria criteria) throws Exception {
        menuService.download(menuService.queryAll(criteria, false), response);
    }

    @GetMapping(value = "/build")
    public ResponseEntity<Object> buildMenus(RequestPostVo vo) {
        List<MenuDto> menuDtoList = menuService.findByUser(vo.getUserInfo().getUserId());
        List<MenuDto> menuDtos = menuService.buildTree(menuDtoList);
        return new ResponseEntity<>(menuService.buildMenus(menuDtos), HttpStatus.OK);
    }

    @GetMapping(value = "/lazy")
    @UserPermission("menu:list,roles:list")
    public ResponseEntity<Object> query(@RequestParam Long pid) {
        return new ResponseEntity<>(menuService.getMenus(pid), HttpStatus.OK);
    }

    @Log("查询菜单")
    @GetMapping
    @UserPermission("menu:list")
    public ResponseEntity<Object> query(MenuQueryCriteria criteria) throws Exception {
        List<MenuDto> menuDtoList = menuService.queryAll(criteria, true);
        return new ResponseEntity<>(PageUtil.toPage(menuDtoList, menuDtoList.size()), HttpStatus.OK);
    }

    @Log("查询菜单")
    @PostMapping("/superior")
    @UserPermission("menu:list")
    public ResponseEntity<Object> getSuperior(@RequestBody List<Long> ids) {
        Set<MenuDto> menuDtos = new LinkedHashSet<>();
        if (CollectionUtil.isNotEmpty(ids)) {
            for (Long id : ids) {
                MenuDto menuDto = menuService.findById(id);
                menuDtos.addAll(menuService.getSuperior(menuDto, new ArrayList<>()));
            }
            return new ResponseEntity<>(menuService.buildTree(new ArrayList<>(menuDtos)), HttpStatus.OK);
        }
        return new ResponseEntity<>(menuService.getMenus(null), HttpStatus.OK);
    }

    @Log("新增菜单")
    @PostMapping
    @UserPermission("menu:add")
    public ResponseEntity<Object> create(@Validated @RequestBody Menu resources) {
        if (resources.getId() != null) {
            throw GlobalException.createParam("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        menuService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改菜单")
    @PutMapping
    @UserPermission("menu:edit")
    public ResponseEntity<Object> update(@Validated(Menu.Update.class) @RequestBody Menu resources) {
        menuService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除菜单")
    @DeleteMapping
    @UserPermission("menu:del")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        Set<Menu> menuSet = new HashSet<>();
        for (Long id : ids) {
            List<MenuDto> menuList = menuService.getMenus(id);
            menuSet.add(menuService.findOne(id));
            menuSet = menuService.getDeleteMenus(menuMapper.toEntity(menuList), menuSet);
        }
        menuService.delete(menuSet);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
