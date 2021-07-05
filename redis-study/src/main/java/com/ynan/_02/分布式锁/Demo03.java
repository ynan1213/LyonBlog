package com.ynan._02.分布式锁;

import redis.clients.jedis.Jedis;

/**
 * @program: redis-study
 * @description: 02 如果执行业务代码的时候JVM进程被杀死了，也可能会导致锁释放不了，解决方案，加过期时间
 * @author: yn
 * @create: 2021-07-01 20:52
 */
public class Demo03
{
    public static void main(String[] args)
    {
        Jedis jedis = new Jedis("localhost", 6379);
        Long result = jedis.setnx("key", "on");
        if (result < 1)
        {
            // jedis如何保证上面的setnx和expire的原子性？？？
            jedis.expire("key", 10);
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
