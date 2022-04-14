package com.ynan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author yuannan
 * @Date 2022/4/14 23:01
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class SentinelFeignMain {

    public static void main(String[] args) {
        SpringApplication.run(SentinelFeignMain.class, args);
    }
}
