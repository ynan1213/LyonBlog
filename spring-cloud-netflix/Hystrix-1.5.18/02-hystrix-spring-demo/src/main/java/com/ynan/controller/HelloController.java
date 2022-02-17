package com.ynan.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import java.util.concurrent.TimeUnit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/1/13 10:43
 */
@RestController
public class HelloController {

    @HystrixCommand(fallbackMethod = "helloMethodHystrixFallback")
    @RequestMapping("/hello")
    public String hello() {
        System.out.println("------------");
        try {
            TimeUnit.SECONDS.sleep(15);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello world";
    }

    public String helloMethodHystrixFallback() {
        System.out.println("==========================================");
//        throw new RuntimeException("xxx");
        return "降级返回 : hello world";
    }
}
