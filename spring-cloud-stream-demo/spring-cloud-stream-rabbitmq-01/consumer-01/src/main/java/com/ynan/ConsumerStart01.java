package com.ynan;

import com.ynan.mq.ConsumerInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @Author yuannan
 * @Date 2021/11/20 12:24
 */
@SpringBootApplication
@EnableBinding(ConsumerInterface.class)
public class ConsumerStart01 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerStart01.class, args);
    }
}
