package com.thatday.config;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@ConfigurationProperties(prefix = "spring.redis.cluster")
@Configuration
@Data
public class RedissonConfig {

    private List<String> nodes;

    @Bean
    public RedissonClient redissonClient(Environment environment) {
        Config config = new Config();
        if (CollUtil.isNotEmpty(nodes)) {
            ClusterServersConfig clusterServersConfig = config.useClusterServers().setScanInterval(2000);
            String[] hosts = new String[nodes.size()];
            for (int i = 0; i < nodes.size(); i++) {
                hosts[i] = "redis://" + nodes.get(i);
            }
            clusterServersConfig.addNodeAddress(hosts);

        } else {
            String host = environment.getProperty("spring.redis.host");
            String port = environment.getProperty("spring.redis.port");
            String psw = environment.getProperty("spring.redis.password");
            host = StrUtil.isEmpty(host) ? "127.0.0.1" : host;
            port = StrUtil.isEmpty(port) ? "6379" : port;
            config.useSingleServer()
                    .setTimeout(10000)
                    .setAddress("redis://" + host + ":" + port)
                    .setPassword(psw);
        }

        return Redisson.create(config);
    }
}
