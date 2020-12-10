package com.thatday.controller;

import com.thatday.config.EnvConfig;
import io.swagger.annotations.Api;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Api(tags = "公共模块")
public class TestController {

    @Autowired
    EnvConfig envConfig;

    @Autowired
    RedissonClient redissonClient;

    @GetMapping("/common/testDLock")
    public String testDLock() {
        //分布式锁测试
        RLock rLock = redissonClient.getLock("aaa");
        try {
            boolean tryLock = rLock.tryLock(3, TimeUnit.SECONDS);
            if (tryLock) {
                try {
                    return envConfig.getPort() + "-test-" + System.currentTimeMillis();
                } finally {
                    rLock.unlock();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return "no lock";
    }

}
