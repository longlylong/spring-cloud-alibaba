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

    @GetMapping("/common/test")
    public String test() {
        RLock aaa = redissonClient.getLock("aaa");
        aaa.lock(10, TimeUnit.SECONDS);
        try {
            System.out.println("222222222222222");
        } finally {
            aaa.unlock();
        }

        return envConfig.getPort() + "-test-" + System.currentTimeMillis();
    }

}
