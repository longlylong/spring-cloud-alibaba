package com.thatday.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.thatday.user.entity.db.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${server.port}")
    private int port;

    //这里一定要加一个资源才会抛抛异常的
    @SentinelResource(value = "sendSendSend")
    @GetMapping(value = "/send")
    public String sent() throws Exception {
        User user = new User();
        User userB = new User();
//
//        long s = System.currentTimeMillis();
//        RocketMQUtil.send(RocketMQConfig.Topic, RocketMQConfig.Tags_A, user);
        return System.currentTimeMillis() + "-" + port + "-";
    }


}
