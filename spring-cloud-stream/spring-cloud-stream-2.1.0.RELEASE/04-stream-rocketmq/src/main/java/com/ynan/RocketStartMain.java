package com.ynan;

import com.ynan.mq.XxxMessageBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
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

        SubscribableChannel subscribableChannel = bind.xxxInput();
        bind.xxxOutput().send(MessageBuilder.withPayload("xxx-yyyy").build());
        System.out.println("发送消息成功");
    }
}
