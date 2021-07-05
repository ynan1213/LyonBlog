package com.ynan._02.分布式锁;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @program: redis-study
 * @description:
 *
 * 04中还有个问题，如果A线程获取到锁并执行业务方法的时候卡了10s钟，导致key过期
 * 此时有另一个线程获取到锁并进来，紧接着A执行到finally中，删除了该key，然后又有线程获取到锁
 *
 * 解决方法：每个线程生成一个uuid，保存自己的uuid，
 *
 * @author: yn
 * @create: 2021-07-01 20:52
 */
public class Demo05
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
            // 获取到锁，执行业务代码
            System.out.println("成功获取到锁");
        } finally
        {
            // 只有当存的是自己的uuid的时候才去删除
            if(uuid.equals(redisTemplate.opsForValue().get("key")))
            {
                // 这里还是有个问题，如果if进来的时候刚好过期了，到这里删除的又是另一个线程加的锁
                redisTemplate.delete("key");
            }
        }
    }
}
