package com.thatday.user.rocketmq;

import com.thatday.common.rocketmp.RocketMQUtil;
import com.thatday.user.entity.User;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RocketMQConfig implements ApplicationRunner {

    public static final String Topic = "bbb";
    public static final String Tags_A = "a";
    public static final String Tags_B = "b";
    private static final String RocketMQGroup = "User";
    private static final String RocketMQGroupB = "UserB";

    @Value("${server.port}")
    private int port;

    @Override
    public void run(ApplicationArguments args) {
        RocketMQUtil.initProducer(RocketMQGroup, RocketMQUtil.DefaultNameSrvAddr);

        RocketMQUtil.initConsumer(port, RocketMQGroup, RocketMQUtil.DefaultNameSrvAddr, Topic, Tags_A, (msgList, context) -> {
            User user = RocketMQUtil.getData(msgList, User.class);
            System.out.println(user);

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        RocketMQUtil.initConsumer(port, RocketMQGroupB, RocketMQUtil.DefaultNameSrvAddr, Topic, Tags_B, (msgList, context) -> {

            User user = RocketMQUtil.getData(msgList, User.class);
            System.out.println(user);

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
    }
}
