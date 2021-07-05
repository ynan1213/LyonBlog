package com.ynan._02.分布式锁;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: redis-study
 * @description: redisson已经实现了分布式锁
 * @author: yn
 * @create: 2021-07-01 20:52
 */
public class Demo07
{
    public static void main(String[] args)
    {
        RedissonClient redissonClient = Redisson.create();

        RLock lock = redissonClient.getLock("key");
        lock.lock();
        try
        {
            // 执行业务代码
            System.out.println("成功获取到锁");
        } finally
        {
            lock.unlock();
        }
    }
}
