package com.thatday.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.thatday.common.model.NextIdVo;
import com.thatday.common.model.ResponseModel;
import com.thatday.common.rocketmp.RocketMQUtil;
import com.thatday.user.entity.User;
import com.thatday.user.repository.UserRepository;
import com.thatday.user.rocketmq.RocketMQConfig;
import com.thatday.user.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommonService commonService;

    @Value("${server.port}")
    private int port;

    public String sentinelHandler(BlockException ex) {
        return ResponseModel.buildSentinelError();
    }

    @GetMapping(value = "/send")
    public String sent() throws Exception {
        User user = new User();
        User userB = new User();

        user.setNickname("aaa");
        userB.setNickname("bbb");
        long s = System.currentTimeMillis();
        RocketMQUtil.send(RocketMQConfig.Topic, RocketMQConfig.Tags_A, user);
        return System.currentTimeMillis() + "-" + port + "-" + (System.currentTimeMillis() - s);
    }

    @SentinelResource(value = "getAAAAAAAAAA", blockHandler = "sentinelHandler")
    @GetMapping(value = "/getAccount")
    public String getAccount() {
        List<User> user = userRepository.findAll();
        return System.currentTimeMillis() + " " + port + "</br>" + user.toString();
    }

    @GetMapping(value = "/nextIdFormatSimple")
    public String nextIdFormatSimple() {
        NextIdVo nextIdVo = NextIdVo.of("UserId", "e051e12a920bc015c57759cc017ce00a");
        return commonService.nextIdFormatSimple(nextIdVo);
    }

    @GetMapping(value = "/saveAccount")
    public User saveAccount() {
        User user = new User();
        user.setNickname("afaf");
        user.setPassword("afaf");
        user.setPhone("13413513467");
        userRepository.save(user);
        System.out.println(user);
        return user;
    }


}
