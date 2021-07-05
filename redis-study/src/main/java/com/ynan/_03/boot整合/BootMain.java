package com.ynan._03.boot整合;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: redis-study
 * @description:
 * @author: yn
 * @create: 2021-07-01 21:30
 */
@SpringBootApplication
@RestController
public class BootMain
{
    @Autowired
    private RedisTemplate redisTemplate;

    public static void main(String[] args)
    {
        SpringApplication.run(BootMain.class, args);
    }

    @RequestMapping("/hello")
    public String hello()
    {
        redisTemplate.opsForValue().set("k1", "v1");
        System.out.println(redisTemplate.opsForValue().get("k1"));
        return redisTemplate.opsForValue().get("k1").toString();
    }


}
