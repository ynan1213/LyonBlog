package com.ynan;

import com.ynan.mq.XxxMessageBinding;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @Author yuannan
 * @Date 2021/11/22 15:49
 */
@SpringBootApplication
@EnableAspectJAutoProxy
@EnableBinding(XxxMessageBinding.class)
public class GrayStartMain {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(GrayStartMain.class, args);

        XxxMessageBinding bind = context.getBean(XxxMessageBinding.class);

        for (int i = 0; i < 2; i++) {
            bind.xxxOutput().send(MessageBuilder.withPayload("aaaaaa-bbbbbbb").build());
        }
    }
}
