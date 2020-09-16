package com.mm.admin.common.config;

import com.mm.admin.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppStartConfig implements ApplicationRunner {

    @Autowired
    RedisUtils redisUtils;

    @Override
    public void run(ApplicationArguments args) {
        redisUtils.clearAll();
    }
}
