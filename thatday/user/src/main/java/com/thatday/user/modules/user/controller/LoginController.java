package com.thatday.user.modules.user.controller;

import com.thatday.common.dubbo.CommonService;
import com.thatday.common.model.Result;
import com.thatday.common.utils.ThreadUtil;
import com.thatday.user.config.EnvConfig;
import com.thatday.user.modules.user.entity.User;
import com.thatday.user.modules.user.service.UserService;
import com.thatday.user.modules.user.vo.LoginPhoneVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    RedissonClient redissonClient;

    @ApiOperation("test")
    @GetMapping(value = "/testSeata")
//    @GlobalTransactional
    public Result<Object> testSeata() {
        //全局事务测试
        userService.addUser("nickname" + System.currentTimeMillis());
        String s = commonService.dubboTest();
        return Result.buildSuccess();
    }

    @GetMapping(value = "/testDLock")
    public Result<Object> testDLock() {
        //分布式锁测试
        RLock rLock = redissonClient.getLock("aaa");
        rLock.lock(10, TimeUnit.SECONDS);
        try {
            System.out.println("test2 RedissonClient lock sleep 3000");
            ThreadUtil.sleep(2000);
            System.out.println("test2 RedissonClient lock sleep over");
        } finally {
            rLock.unlock();
        }
        return Result.buildSuccess(envConfig.getPort());
    }

    @ApiOperation("手机号登录接口")
    @PostMapping(value = "/loginByPhone")
    public Result<User> loginByPhone(@Valid @RequestBody LoginPhoneVo vo) {
        User user = userService.loginByPhone(vo);
        return Result.buildSuccess(user);
    }
}
