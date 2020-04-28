package com.thatday.user.controller.user;

import com.thatday.common.model.Result;
import com.thatday.user.entity.db.User;
import com.thatday.user.entity.vo.LoginPhoneVo;
import com.thatday.user.entity.vo.LoginWeChatVo;
import com.thatday.user.service.user.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@Api(tags = "登录类API")
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @ApiOperation("手机号登录接口")
    @PostMapping(value = "/loginByPhone")
    public Result<User> loginByPhone(@Valid @RequestBody LoginPhoneVo loginPhoneVo) {
        User user = loginService.loginByPhone(loginPhoneVo);

        return Result.buildSuccess(user);
    }

    @ApiOperation("微信登录接口")
    @PostMapping(value = "/loginByWeChat")
    public Result<User> loginByWeChat(@Valid @RequestBody LoginWeChatVo loginWeChatVo) {
        User user = loginService.loginByWeChat(loginWeChatVo);
        return Result.buildSuccess(user);
    }
}
