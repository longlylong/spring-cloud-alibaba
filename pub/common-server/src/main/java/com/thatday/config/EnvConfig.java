package com.thatday.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Data
@Component
public class EnvConfig {

    @Value("${server.port}")
    private int port;

    @Value("${td.swagger.enable}")
    private boolean enableSwagger;

    @Value("${spring.profiles.active}")
    private String profile;

    public boolean isLocal() {
        return "local".equals(profile);
    }
}
