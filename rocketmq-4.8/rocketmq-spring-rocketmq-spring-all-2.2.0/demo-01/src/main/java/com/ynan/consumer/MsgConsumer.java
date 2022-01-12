package com.ynan.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQReplyListener;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2022/1/12 21:25
 */
@Component
@RocketMQMessageListener(consumerGroup = "consumer_group", topic = "consumer_topic")
public class MsgConsumer implements RocketMQReplyListener<String, String> { // 也可以实现 RocketMQListener 接口

    @Override
    public String onMessage(String message) {
        System.out.println(message);
        return "hello ====== " + message;
    }
}
