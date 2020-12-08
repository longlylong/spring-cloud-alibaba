package com.thatday.user.modules.user.controller;

import com.thatday.common.dubbo.CommonService;
import com.thatday.common.model.Result;
import com.thatday.user.config.EnvConfig;
import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.service.UserService;
import com.thatday.user.modules.user.vo.LoginPhoneVo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/app/user/login")
@Api(tags = "前台 API")
public class LoginController {

    @Autowired
    EnvConfig envConfig;
    @Autowired
    UserService userService;

    @DubboReference
    CommonService commonService;

    @ApiOperation("test")
    @GetMapping(value = "/testSeata")
    @GlobalTransactional
    public Result<Object> testSeata() {
        userService.addUser("nickname" + System.currentTimeMillis());
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
}
