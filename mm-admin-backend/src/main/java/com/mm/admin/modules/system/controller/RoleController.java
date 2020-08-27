package com.mm.admin.modules.system.controller;

import cn.hutool.core.lang.Dict;
import com.mm.admin.common.annotation.UserPermission;
import com.mm.admin.common.exception.BadRequestException;
import com.mm.admin.common.utils.ValidationUtil;
import com.mm.admin.modules.logging.annotation.Log;
import com.mm.admin.modules.system.domain.Role;
import com.mm.admin.modules.system.domain.vo.RoleVo;
import com.mm.admin.modules.system.service.RoleService;
import com.mm.admin.modules.system.service.dto.RoleDto;
import com.mm.admin.modules.system.service.dto.RoleQueryCriteria;
import com.mm.admin.modules.system.service.dto.RoleSmallDto;
import com.thatday.common.model.RequestPostVo;
import com.thatday.common.token.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {

    private static final String ENTITY_NAME = "role";
    private final RoleService roleService;

    @GetMapping(value = "/{id}")
    @UserPermission("roles:list")
    public ResponseEntity<Object> query(@PathVariable Long id) {
        return new ResponseEntity<>(roleService.findById(id), HttpStatus.OK);
    }

    @Log("导出角色数据")
    @GetMapping(value = "/download")
    @UserPermission("role:list")
    public void download(HttpServletResponse response, RoleQueryCriteria criteria) throws IOException {
        roleService.download(roleService.queryAll(criteria), response);
    }

    @GetMapping(value = "/all")
    @UserPermission("roles:list,user:add,user:edit)")
    public ResponseEntity<Object> query() {
        return new ResponseEntity<>(roleService.queryAll(), HttpStatus.OK);
    }

    @Log("查询角色")
    @GetMapping
    @UserPermission("roles:list")
    public ResponseEntity<Object> query(RoleQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(roleService.queryAll(criteria, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/level")
    public ResponseEntity<Object> getLevel(RequestPostVo vo) {
        return new ResponseEntity<>(Dict.create().set("level", getLevels(vo.getUserInfo(), null)), HttpStatus.OK);
    }

    @Log("新增角色")
    @PostMapping
    @UserPermission("roles:add")
    public ResponseEntity<Object> create(@Validated @RequestBody RoleVo vo) {
        if (vo.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        getLevels(vo.getUserInfo(), vo.getLevel());
        roleService.create(vo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改角色")
    @PutMapping
    @UserPermission("roles:edit")
    public ResponseEntity<Object> update(@Validated(Role.Update.class) @RequestBody RoleVo vo) {
        getLevels(vo.getUserInfo(), vo.getLevel());
        roleService.update(vo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("修改角色菜单")
    @PutMapping(value = "/menu")
    @UserPermission("roles:edit")
    public ResponseEntity<Object> updateMenu(@RequestBody RoleVo vo) {
        RoleDto role = roleService.findById(vo.getId());
        getLevels(vo.getUserInfo(), role.getLevel());
        roleService.updateMenu(vo, role);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除角色")
    @DeleteMapping
    @UserPermission("roles:del")
    public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody Set<Long> ids) {
        UserInfo userInfo = ValidationUtil.getUserInfo(request);

        for (Long id : ids) {
            RoleDto role = roleService.findById(id);
            getLevels(userInfo, role.getLevel());
        }
        // 验证是否被用户关联
        roleService.verification(ids);
        roleService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 获取用户的角色级别
     */
    private int getLevels(UserInfo userInfo, Integer level) {
        List<Integer> levels = roleService.findByUsersId(userInfo.getUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList());
        int min = Collections.min(levels);
        if (level != null) {
            if (level < min) {
                throw new BadRequestException("权限不足，你的角色级别：" + min + "，低于操作的角色级别：" + level);
            }
        }
        return min;
    }
}
