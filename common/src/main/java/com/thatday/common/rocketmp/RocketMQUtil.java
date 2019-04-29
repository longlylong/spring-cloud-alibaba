package com.thatday.common.rocketmp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thatday.common.exception.GlobalException;
import com.thatday.common.utils.TemplateCodeUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

public class RocketMQUtil {

    public static String DefaultNameSrvAddr = "127.0.0.1:9876";
    private static DefaultMQProducer producer;

    public static void initProducer(String producerGroup, String nameSrvAddr) {
        try {
            producer = new DefaultMQProducer(producerGroup);
            producer.setNamesrvAddr(nameSrvAddr);
            producer.start();
            producer.setRetryTimesWhenSendAsyncFailed(0);
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * port + producerGroup + topic + tags 为多实例的名字，确保不会重复消费与接收不到
     */
    public static void initConsumer(int port, String producerGroup, String nameSrvAddr, String topic, String tags,
                                    MessageListenerConcurrently listener) {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(producerGroup);
            consumer.setNamesrvAddr(nameSrvAddr);
            consumer.setInstanceName(port + "-" + producerGroup + "-" + topic + "-" + tags);
            consumer.subscribe(topic, tags);
            consumer.registerMessageListener(listener);
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送时只需要区分 topic tags对应的Consumer就会收到不同的消息
     */
    public static void send(String topic, String tags, Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonStr = objectMapper.writeValueAsString(obj);
            send(topic, tags, jsonStr, null);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void send(String topic, String tags, String content) {
        send(topic, tags, content, null);
    }

    private static void send(String topic, String tags, String content, SendCallback callback) {
        isNull();
        try {
            Message msg = new Message(topic, tags, content.getBytes(RemotingHelper.DEFAULT_CHARSET));
            if (callback == null) {
                producer.send(msg);
            } else {
                producer.send(msg, callback);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T getData(List<MessageExt> msgList, Class<T> clazz) {
        MessageExt messageExt = msgList.get(0);
        return TemplateCodeUtil.jsonToObject(messageExt.getBody(), clazz);
    }


    private static void isNull() {
        if (producer == null) {
            throw new GlobalException("Please InitProducer");
        }
    }
}
