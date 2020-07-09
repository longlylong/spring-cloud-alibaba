package com.thatday.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @Value("${batch.size.max}")
    private Integer batchSizeMax;

    @Value("${server.port}")
    private int port;


    @GetMapping("/test")
    public String test() {
        return port + "-test-" + System.currentTimeMillis();
    }


}
