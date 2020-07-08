package com.thatday.user.rocketmq;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RocketMQConfig implements ApplicationRunner {

    public static final String Topic = "bbb" ;
    public static final String Topic_A = "aaa" ;
    public static final String Tags_A = "a" ;
    public static final String Tags_B = "b" ;


    @Override
    public void run(ApplicationArguments args) {
//        RocketMQUtil.send(RocketMQConfig.Topic, RocketMQConfig.Tags_A, obj);

//        RocketMQUtil.initDefaultProducer();

//        RocketMQUtil.initConsumer(port, Topic, Tags_A, (msgList, context) -> {
//            User user = RocketMQUtil.getData(msgList, User.class);
//            System.out.println(user);
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//        });

//        RocketMQUtil.initConsumer(port, Topic, Tags_B, (msgList, context) -> {
//
//            FileGroup groupInfo = RocketMQUtil.getData(msgList, FileGroup.class);
//            System.out.println(groupInfo);
//            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//        });
    }
}
