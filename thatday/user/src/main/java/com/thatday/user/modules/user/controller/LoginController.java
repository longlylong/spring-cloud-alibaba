package com.thatday.user.modules.user.controller;

import com.thatday.common.model.Result;
import com.thatday.user.config.EnvConfig;
import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.service.UserService;
import com.thatday.user.modules.user.vo.LoginPhoneVo;
import com.thatday.user.modules.user.vo.LoginWeChatVo;
import com.thatday.common.dubbo.CommonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/app/user/login")
@Api(tags = "登录类API")
public class LoginController {

    @Autowired
    EnvConfig envConfig;
    @Autowired
    UserService userService;

    @DubboReference(check = false)
    CommonService commonService;

    @ApiOperation("test")
    @GetMapping(value = "/test1")
    public Result<Object> test1() {
        return Result.buildSuccess(commonService.test());
    }

    @GetMapping(value = "/test2")
    public Result<Object> test2() {
        return Result.buildSuccess(envConfig.getPort());
    }

    @ApiOperation("手机号登录接口")
    @PostMapping(value = "/loginByPhone")
    public Result<User> loginByPhone(@Valid @RequestBody LoginPhoneVo vo) {
        User user = userService.loginByPhone(vo);
        return Result.buildSuccess(user);
    }

    @ApiOperation("微信登录接口")
    @PostMapping(value = "/loginByWeChat")
    public Result<User> loginByWeChat(@Valid @RequestBody LoginWeChatVo loginWeChatVo) {
        User user = userService.loginByWeChat(loginWeChatVo);
        return Result.buildSuccess(user);
    }
}
