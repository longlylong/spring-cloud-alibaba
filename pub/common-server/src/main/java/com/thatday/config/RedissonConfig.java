package com.thatday.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
//        config.useClusterServers()
//                .setScanInterval(2000)
//                .addNodeAddress("redis://192.168.220.130");
        config.useSingleServer()
                .setTimeout(1000000)
                .setAddress("redis://192.168.220.130:6379")
                .setPassword("123456789");
        return Redisson.create(config);
    }
}
