package com.ynan._01.jedis用法;

import redis.clients.jedis.Jedis;

/**
 * @program: redis-study
 * @description:
 * @author: yn
 * @create: 2021-07-01 20:43
 */
public class JedisMain
{
    public static void main(String[] args)
    {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis.ping());

        System.out.println(jedis.setnx("key1", "value1"));
        System.out.println(jedis.setnx("key1", "value1"));
    }
}
