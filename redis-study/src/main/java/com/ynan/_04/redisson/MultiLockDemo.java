package com.ynan._04.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * @Author yuannan
 * @Date 2022/5/7 15:27
 */
public class MultiLockDemo {

    /**
     * 如果我们需要同时锁定N个资源呢：
     *  例如下单的时候，我们需要同时锁定订单、库存、商品等
     */
    public static void main(String[] args) {

        RedissonClient client = Redisson.create();

        RLock lock1 = client.getLock("订单锁");
        RLock lock2 = client.getLock("库存锁");
        RLock lock3 = client.getLock("商品锁");

        RLock multiLock = client.getMultiLock(lock1, lock2, lock3);

        multiLock.lock();

        multiLock.unlock();

    }

}
