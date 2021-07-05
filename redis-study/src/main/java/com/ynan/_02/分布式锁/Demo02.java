package com.ynan._02.分布式锁;

import redis.clients.jedis.Jedis;

/**
 * @program: redis-study
 * @description: 01 中的业务代码会抛异常，导致锁释放不了，解决方案：加 try finally
 * @author: yn
 * @create: 2021-07-01 20:52
 */
public class Demo02
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
        try
        {
            // 获取到锁，执行业务代码
            System.out.println("成功获取到锁");
        } finally
        {
            jedis.del("key");
        }
    }
}
