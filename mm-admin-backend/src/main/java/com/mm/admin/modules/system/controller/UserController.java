package com.mm.admin.modules.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.mm.admin.common.annotation.UserPermission;
import com.mm.admin.common.config.RsaProperties;
import com.mm.admin.common.utils.PageUtil;
import com.mm.admin.common.utils.RsaUtils;
import com.mm.admin.common.utils.ValidationUtil;
import com.mm.admin.modules.logging.annotation.Log;
import com.mm.admin.modules.system.domain.User;
import com.mm.admin.modules.system.domain.vo.UserPassVo;
import com.mm.admin.modules.system.service.DataService;
import com.mm.admin.modules.system.service.DeptService;
import com.mm.admin.modules.system.service.RoleService;
import com.mm.admin.modules.system.service.UserService;
import com.mm.admin.modules.system.service.dto.RoleSmallDto;
import com.mm.admin.modules.system.service.dto.UserDto;
import com.mm.admin.modules.system.service.dto.UserQueryCriteria;
import com.thatday.common.exception.GlobalException;
import com.thatday.common.token.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final DataService dataService;
    private final DeptService deptService;
    private final RoleService roleService;

    @Log("导出用户数据")
    @GetMapping(value = "/download")
    @UserPermission("user:list")
    public void download(HttpServletResponse response, UserQueryCriteria criteria) throws IOException {
        userService.download(userService.queryAll(criteria), response);
    }

    @Log("查询用户")
    @GetMapping
    @UserPermission("user:list")
    public ResponseEntity<Object> query(UserQueryCriteria criteria, Pageable pageable) {
        if (!ObjectUtils.isEmpty(criteria.getDeptId())) {
            criteria.getDeptIds().add(criteria.getDeptId());
            criteria.getDeptIds().addAll(deptService.getDeptChildren(criteria.getDeptId(),
                    deptService.findByPid(criteria.getDeptId())));
        }
        // 数据权限
        List<Long> dataScopes = dataService.getDeptIds(userService.findById(criteria.getUserInfo().getUserId()));
        // criteria.getDeptIds() 不为空并且数据权限不为空则取交集
        if (!CollectionUtils.isEmpty(criteria.getDeptIds()) && !CollectionUtils.isEmpty(dataScopes)) {
            // 取交集
            criteria.getDeptIds().retainAll(dataScopes);
            if (!CollectionUtil.isEmpty(criteria.getDeptIds())) {
                return new ResponseEntity<>(userService.queryAll(criteria, pageable), HttpStatus.OK);
            }
        } else {
            // 否则取并集
            criteria.getDeptIds().addAll(dataScopes);
            return new ResponseEntity<>(userService.queryAll(criteria, pageable), HttpStatus.OK);
        }
        return new ResponseEntity<>(PageUtil.toPage(null, 0), HttpStatus.OK);
    }

    @Log("新增用户")
    @PostMapping
    @UserPermission("user:add")
    public ResponseEntity<Object> create(@Validated @RequestBody User user) {
        checkLevel(user);
        // 默认密码 123456
        user.setPassword(passwordEncoder.encode("123456"));
        userService.create(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Log("修改用户")
    @PutMapping
    @UserPermission("user:edit")
    public ResponseEntity<Object> update(@Validated(User.Update.class) @RequestBody User resources) {
        checkLevel(resources);
        userService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("修改用户：个人中心")
    @PutMapping(value = "center")
    public ResponseEntity<Object> center(@Validated(User.Update.class) @RequestBody User resources) {
        if (!resources.getId().equals(resources.getUserInfo().getUserId())) {
            throw GlobalException.createParam("不能修改他人资料");
        }
        userService.updateCenter(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @DeleteMapping
    @UserPermission("user:del")
    public ResponseEntity<Object> delete(HttpServletRequest request, @RequestBody Set<Long> ids) {
        UserInfo userInfo = ValidationUtil.getUserInfo(request);

        for (Long id : ids) {
            Integer currentLevel = Collections.min(roleService.findByUsersId(userInfo.getUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            Integer optLevel = Collections.min(roleService.findByUsersId(id).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
            if (currentLevel > optLevel) {
                throw GlobalException.createParam("角色权限不足，不能删除：" + userService.findById(id).getUsername());
            }
        }
        userService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/updatePass")
    public ResponseEntity<Object> updatePass(@RequestBody UserPassVo passVo) throws Exception {
        String oldPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getOldPass());
        String newPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getNewPass());
        UserDto user = userService.findById(passVo.getUserInfo().getUserId());
        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            throw GlobalException.createParam("修改失败，旧密码错误");
        }
        if (passwordEncoder.matches(newPass, user.getPassword())) {
            throw GlobalException.createParam("新密码不能与旧密码相同");
        }
        userService.updatePass(user.getUsername(), passwordEncoder.encode(newPass));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/updateAvatar")
    public ResponseEntity<Object> updateAvatar(@RequestParam MultipartFile avatar) {
        return new ResponseEntity<>(userService.updateAvatar(avatar), HttpStatus.OK);
    }

    @Log("修改邮箱")
    @PostMapping(value = "/updateEmail/{code}")
    public ResponseEntity<Object> updateEmail(@PathVariable String code, @RequestBody User vo) throws Exception {
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, vo.getPassword());
        UserDto userDto = userService.findById(vo.getUserInfo().getUserId());
        if (!passwordEncoder.matches(password, userDto.getPassword())) {
            throw GlobalException.createParam("密码错误");
        }
        userService.updateEmail(userDto.getUsername(), vo.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
     */
    private void checkLevel(User resources) {
        Integer currentLevel = Collections.min(roleService.findByUsersId(resources.getUserInfo().getUserId()).stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
        Integer optLevel = roleService.findByRoles(resources.getRoles());
        if (currentLevel > optLevel) {
            throw GlobalException.createParam("角色权限不足");
        }
    }
}
