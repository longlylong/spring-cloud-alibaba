package com.mm.admin.modules.security.controller;

import cn.hutool.core.util.IdUtil;
import com.mm.admin.common.annotation.rest.AnonymousDeleteMapping;
import com.mm.admin.common.annotation.rest.AnonymousGetMapping;
import com.mm.admin.common.annotation.rest.AnonymousPostMapping;
import com.mm.admin.common.config.RsaProperties;
import com.mm.admin.common.exception.BadRequestException;
import com.mm.admin.common.utils.RedisUtils;
import com.mm.admin.common.utils.RsaUtils;
import com.mm.admin.common.utils.StringUtils;
import com.mm.admin.modules.logging.annotation.Log;
import com.mm.admin.modules.security.config.bean.LoginProperties;
import com.mm.admin.modules.security.service.dto.AuthUserDto;
import com.mm.admin.modules.system.domain.User;
import com.mm.admin.modules.system.service.UserService;
import com.mm.admin.modules.system.service.mapstruct.UserMapper;
import com.thatday.common.constant.UserCode;
import com.thatday.common.exception.GlobalException;
import com.thatday.common.model.RequestPostVo;
import com.thatday.common.token.TokenUtil;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @AnonymousPostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUser) throws Exception {
        // 密码解密
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
        // 查询验证码
        String code = (String) redisUtils.get(authUser.getUuid());
        // 清除验证码
        redisUtils.del(authUser.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }
        User user = userService.login(authUser.getUsername(), password);
        if (user == null) {
            throw GlobalException.create(UserCode.UserNotExistCode, UserCode.UserNotExistMsg);
        }
        // 生成令牌
        String token = TokenUtil.getAccessToken(user.getId());

        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", token);
            put("user", userMapper.toDto(user));
        }};

        return ResponseEntity.ok(authInfo);
    }

    @GetMapping(value = "/info")
    public ResponseEntity<Object> getUserInfo(RequestPostVo vo) {
        return ResponseEntity.ok(userService.findById(vo.getUserInfo().getUserId()));
    }

    @AnonymousGetMapping(value = "/code")
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

    @AnonymousDeleteMapping(value = "/logout")
    public ResponseEntity<Object> logout(HttpServletRequest request) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
