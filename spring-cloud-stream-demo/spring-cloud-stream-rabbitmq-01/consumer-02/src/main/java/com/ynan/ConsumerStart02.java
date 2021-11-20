package com.ynan;

import com.ynan.mq.ConsumerInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @Author yuannan
 * @Date 2021/11/20 12:25
 */
@SpringBootApplication
@EnableBinding(ConsumerInterface.class)
public class ConsumerStart02 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerStart02.class, args);
    }
}
