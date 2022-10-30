package com.ynan._04.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * @Author yuannan
 * @Date 2022/5/7 15:16
 */
public class FairLockDemo {

    public static void main(String[] args) {

        RedissonClient client = Redisson.create();

        // 实际类型是 RedissonFairLock
        RLock fairLock = client.getFairLock("key");

    }
}
