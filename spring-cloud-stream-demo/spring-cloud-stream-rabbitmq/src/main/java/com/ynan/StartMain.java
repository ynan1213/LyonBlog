package com.ynan;

import com.ynan.mq.OutputMessageBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @Author yuannan
 * @Date 2021/11/9 10:03
 */
@SpringBootApplication
public class StartMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StartMain.class, args);
        OutputMessageBinding bind = context.getBean(OutputMessageBinding.class);
        bind.xxxxxoutput().send(MessageBuilder.withPayload("xxxx yyyy").build());
        System.out.println("发送消息成功");
    }
}
