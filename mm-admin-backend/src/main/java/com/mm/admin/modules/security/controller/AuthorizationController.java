package com.mm.admin.modules.security.controller;

import cn.hutool.core.util.IdUtil;
import com.mm.admin.common.base.BaseRequestVo;
import com.mm.admin.common.config.RsaProperties;
import com.mm.admin.common.utils.RedisUtils;
import com.mm.admin.common.utils.RsaUtils;
import com.mm.admin.common.utils.StringUtils;
import com.mm.admin.modules.logging.annotation.Log;
import com.mm.admin.modules.security.config.bean.JwtUserDto;
import com.mm.admin.modules.security.config.bean.LoginProperties;
import com.mm.admin.modules.security.vo.AuthUserVo;
import com.mm.admin.modules.system.domain.Role;
import com.mm.admin.modules.system.domain.User;
import com.mm.admin.modules.system.service.UserService;
import com.mm.admin.modules.system.service.mapstruct.UserMapper;
import com.thatday.common.constant.DeviceCode;
import com.thatday.common.constant.UserCode;
import com.thatday.common.exception.GlobalException;
import com.thatday.common.token.TokenUtil;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 授权、根据token获取用户详细信息
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController {

    private final RedisUtils redisUtils;
    private final UserMapper userMapper;
    private final UserService userService;

    @Resource
    private LoginProperties loginProperties;

    @Log("用户登录")
    @PostMapping(value = "/login")
    @Transactional
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserVo authUser) throws Exception {
        // 密码解密
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
        // 查询验证码
        String code = (String) redisUtils.get(authUser.getUuid());
        // 清除验证码
        redisUtils.del(authUser.getUuid());
        if (StringUtils.isBlank(code)) {
            throw GlobalException.createParam("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw GlobalException.createParam("验证码错误");
        }
        User user = userService.login(authUser.getUsername(), password);
        return ResponseEntity.ok(getAuthInfo(user));
    }

    private Map<String, Object> getAuthInfo(User user) {
        if (user == null) {
            throw GlobalException.create(UserCode.UserNotExistCode, UserCode.UserNotExistMsg);
        }

        String role = "";
        if (user.getIsAdmin()) {
            role = Role.ADMIN;
        } else {
            if (user.getRoles().size() == 1) {
                for (Role r : user.getRoles()) {
                    role = r.getName();
                }
            }
        }

        String token = TokenUtil.getAccessToken(user.getId(), role, DeviceCode.WEB_PC);
        JwtUserDto jwtUserDto = new JwtUserDto(userMapper.toDto(user), new ArrayList<>(), user.getRoles());
        // 返回 token 与 用户信息
        return new HashMap<String, Object>(2) {{
            put("token", token);
            put("user", jwtUserDto);
        }};
    }

    @GetMapping(value = "/info")
    public ResponseEntity<Object> getUserInfo(BaseRequestVo vo) {
        User user = userService.getOne(vo.getUserInfo().getUserId());
        return ResponseEntity.ok(getAuthInfo(user));
    }

    @GetMapping(value = "/code")
    public ResponseEntity<Object> getCode() {
        // 获取运算的结果
        Captcha captcha = loginProperties.getCaptcha();
        String uuid = IdUtil.simpleUUID();
        // 保存
        redisUtils.set(uuid, captcha.text(), loginProperties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok(imgResult);
    }

    @DeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
