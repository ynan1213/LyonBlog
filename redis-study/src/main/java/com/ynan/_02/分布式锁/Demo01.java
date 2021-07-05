package com.ynan._02.分布式锁;

import redis.clients.jedis.Jedis;

/**
 * @program: redis-study
 * @description: 最简单版本
 * @author: yn
 * @create: 2021-07-01 20:52
 */
public class Demo01
{
    public static void main(String[] args)
    {
        Jedis jedis = new Jedis("localhost", 6379);

        Long result = jedis.setnx("key", "on");
        if (result < 1)
        {
            System.out.println("未获取到锁");
            return;
        }
        // 获取到锁，执行业务代码
        System.out.println("成功获取到锁");
        jedis.del("key");
    }
}
