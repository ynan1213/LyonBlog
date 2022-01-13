package com.ynan.config;

import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author yuannan
 * @Date 2022/1/13 10:50
 */
@Configuration
public class HystrixConfig {

    @Bean
    public HystrixCommandAspect hystrixAspect() {
        return new HystrixCommandAspect();
    }

}
