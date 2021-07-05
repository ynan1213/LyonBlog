package com.ynan._02.分布式锁;

import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;

import java.util.concurrent.TimeUnit;

/**
 * @program: redis-study
 * @description: 03加过期时间不能保证原子性
 * @author: yn
 * @create: 2021-07-01 20:52
 */
public class Demo04
{
    public static void main(String[] args)
    {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();

        // 可以保证原子性
        Boolean result = redisTemplate.opsForValue().setIfAbsent("key", "on", 10, TimeUnit.SECONDS);
        if (!result)
        {
            System.out.println("未获取到锁");
            return;
        }
        try
        {
            // 获取到锁，执行业务代码
            System.out.println("成功获取到锁");
        } finally
        {
            redisTemplate.delete("key");
        }
    }
}
