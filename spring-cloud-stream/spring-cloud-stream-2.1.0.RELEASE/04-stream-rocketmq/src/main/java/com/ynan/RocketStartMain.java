package com.ynan;

import com.ynan.mq.XxxMessageBinding;
import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @Author yuannan
 * @Date 2022/1/9 20:11
 */
@SpringBootApplication
public class RocketStartMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RocketStartMain.class, args);

        XxxMessageBinding bind = context.getBean(XxxMessageBinding.class);

        SubscribableChannel input = bind.xxxInput();
//        MessageChannel output = bind.xxxOutput();

        // 延迟级别
        // MessageBuilder.withPayload("aaa").setHeader(MessageConst.PROPERTY_DELAY_TIME_LEVEL, 3)
        // 消息 tag
        // MessageBuilder.withPayload("aaa").setHeader(RocketMQHeaders.TAGS, "hello")
        //output.send(MessageBuilder.withPayload("xxx-yyyy").setHeader(RocketMQHeaders.TAGS, "hello").build());

        System.out.println("发送消息成功");
    }
}
