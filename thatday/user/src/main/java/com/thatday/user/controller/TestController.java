package com.thatday.user.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.thatday.common.model.NextIdVo;
import com.thatday.common.rocketmp.RocketMQUtil;
import com.thatday.user.entity.db.GroupInfo;
import com.thatday.user.entity.db.User;
import com.thatday.user.rocketmq.RocketMQConfig;
import com.thatday.user.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Value("${server.port}")
    private int port;

    @Autowired
    CommonService commonService;

    @GetMapping(value = "/getId")
    public String getId() {
        String simple = commonService.nextIdFormatSimple(NextIdVo.orderReqVo());
        System.out.println(simple);
        return simple;
    }


    //这里一定要加一个资源才会抛抛异常的
    @SentinelResource()
    @GetMapping(value = "/send")
    public String sent() throws Exception {
        User user = new User();
        user.setId(System.currentTimeMillis());
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setUserId(user.getId());

        long s = System.currentTimeMillis();
        RocketMQUtil.send(RocketMQConfig.Topic, RocketMQConfig.Tags_A, user);
        RocketMQUtil.send(RocketMQConfig.Topic, RocketMQConfig.Tags_B, groupInfo);
        return System.currentTimeMillis() + "-" + port + "-";
    }


}
