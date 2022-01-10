package com.ynan;

import com.ynan.mq.XxxMessageBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @Author yuannan
 * @Date 2021/11/9 10:03
 */
@SpringBootApplication
public class StartMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StartMain.class, args);
        XxxMessageBinding bind = context.getBean(XxxMessageBinding.class);

        Object xxxInput = context.getBean(XxxMessageBinding.INPUT);
//        Object xxxOutput = context.getBean(XxxMessageBinding.OUTPUT);

        SubscribableChannel subscribableChannel = bind.xxxInput();
//        MessageChannel messageChannel = bind.xxxOutput();
//        MessageChannel messageChannel1 = bind.yyyOutput();

        MessageChannel messageChannel = bind.xxxOutput();
//        messageChannel.send(MessageBuilder.withPayload("xxxx-yyyy").build());
        // 发送带 routingKey
        messageChannel.send(MessageBuilder.withPayload("xxx-yyy").setHeader("shopId", "xxx").build());
        messageChannel.send(MessageBuilder.withPayload("xxx-yyy").setHeader("shopId", "xxx").build());
        messageChannel.send(MessageBuilder.withPayload("xxx-yyy").setHeader("shopId", "xxx").build());
        messageChannel.send(MessageBuilder.withPayload("xxx-yyy").setHeader("shopId", "yyy").build());
        System.out.println("发送消息成功");
    }
}
