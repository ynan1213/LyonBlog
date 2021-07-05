package com.ynan._02.分布式锁;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: redis-study
 * @description:
 *
 * 05 中的问题：删除锁的时候进入if时刚好过期了，就会导致删除的是另一个线程加的锁
 *
 * 解决方法：锁续命，获取锁后新开个子线程，定时查询锁时间，如果锁还在的话重新将锁时间设置为10s
 *
 *
 * @author: yn
 * @create: 2021-07-01 20:52
 */
public class Demo06
{
    public static void main(String[] args)
    {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();

        // 每个线程生成一个uuid
        String uuid = UUID.randomUUID().toString();
        // 保存的是自己的uuid
        Boolean result = redisTemplate.opsForValue().setIfAbsent("key", uuid, 10, TimeUnit.SECONDS);
        if (!result)
        {
            System.out.println("未获取到锁");
            return;
        }
        try
        {
            // 获取到锁，在这里新开子线程，进行锁续命

            // 执行业务代码
            System.out.println("成功获取到锁");
        } finally
        {
            if(uuid.equals(redisTemplate.opsForValue().get("key")))
            {
                redisTemplate.delete("key");
            }
        }
    }
}
