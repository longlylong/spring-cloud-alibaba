package com.thatday.controller;

import com.thatday.config.EnvConfig;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "公共模块")
public class TestController {

    @Autowired
    EnvConfig envConfig;

    @GetMapping("/common/test")
    public String test() {
        return envConfig.getPort() + "-test-" + System.currentTimeMillis();
    }

}
