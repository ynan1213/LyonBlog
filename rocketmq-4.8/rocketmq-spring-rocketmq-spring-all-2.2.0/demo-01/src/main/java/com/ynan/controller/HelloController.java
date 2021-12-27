package com.ynan.controller;

import javax.annotation.Resource;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2021/12/14 15:28
 */
@RestController
public class HelloController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping("/hello")
    public String hello() {
        SendResult sendResult = rocketMQTemplate.syncSend("xxx:abc", "hello world");
        return sendResult.toString();
    }
}
