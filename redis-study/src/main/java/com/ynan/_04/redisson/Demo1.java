package com.ynan._04.redisson;

import java.util.concurrent.TimeUnit;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * @Author yuannan
 * @Date 2022/5/7 14:58
 */
public class Demo1 {

    public static void main(String[] args) throws InterruptedException {
        RedissonClient client = Redisson.create();

        RLock lock = client.getLock("key");

        // 拿锁失败时会不停的重试
        // 具有Watch Dog 自动延期机制 默认续30s 每隔30/3=10 秒续到30s
        lock.lock();

        // 拿锁失败时会不停的重试
        // 没有Watch Dog ，10s后自动释放
        lock.lock(10, TimeUnit.SECONDS);


        lock.tryLock();

        // 尝试拿锁10s后停止重试,返回false
        // 具有Watch Dog 自动延期机制 默认续30s
        boolean res1 = lock.tryLock(10, TimeUnit.SECONDS);

        // 尝试拿锁100s后停止重试,返回false
        // 没有Watch Dog ，10s后自动释放
        boolean res2 = lock.tryLock(100, 10, TimeUnit.SECONDS);




    }

}
