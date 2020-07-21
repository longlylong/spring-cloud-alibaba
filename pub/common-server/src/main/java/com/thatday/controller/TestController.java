package com.thatday.controller;

import com.thatday.config.EnvConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    EnvConfig envConfig;

    @GetMapping("/common/test")
    public String test() {
        return envConfig.getPort() + "-test-" + System.currentTimeMillis();
    }

}
