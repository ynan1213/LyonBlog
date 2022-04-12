/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.slots.block.flow.controller;

import com.alibaba.csp.sentinel.node.Node;
import com.alibaba.csp.sentinel.slots.block.flow.TrafficShapingController;
import com.alibaba.csp.sentinel.util.TimeUtil;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jialiang.linjl
 * <p>
 * 匀速排队策略实现类
 */
public class RateLimiterController implements TrafficShapingController {

    // 排队等待的最大超时时间，如果等待超过该时间，将会抛出 FlowException。
    private final int maxQueueingTimeMs;

    // 流控规则中的阔值，对应FlowRule的count，以QPS为例，如果该值设置为1000，则表示1s可并发的请求数量
    private final double count;

    // 上一次成功通过的时间戳
    private final AtomicLong latestPassedTime = new AtomicLong(-1);

    public RateLimiterController(int timeOut, double count) {
        this.maxQueueingTimeMs = timeOut;
        this.count = count;
    }

    @Override
    public boolean canPass(Node node, int acquireCount) {
        return canPass(node, acquireCount, false);
    }

    @Override
    public boolean canPass(Node node, int acquireCount, boolean prioritized) {
        // Pass when acquire count is less or equal than 0.
        if (acquireCount <= 0) {
            return true;
        }
        // Reject when count is less or equal than 0.
        // Otherwise,the costTime will be max of long and waitTime will overflow in some cases.
        if (count <= 0) {
            return false;
        }

        long currentTime = TimeUtil.currentTimeMillis();

        // 首先算出每一个请求之间最小的间隔，时间单位为毫秒。
        // 例如 count 设置为 1000，表示一秒可以通过 1000个请求，匀速排队，那每个请求的间隔为 1 / 1000(s)，
        // 乘以1000将时间单位转换为毫秒，如果一次需要2个令牌，则其间隔时间为2ms，用 costTime 表示
        long costTime = Math.round(1.0 * (acquireCount) / count * 1000);

        // 计算下一个请求的期望达到时间，等于上一次通过的时间戳 + costTime
        long expectedTime = costTime + latestPassedTime.get();

        // 如果 expectedTime 小于等于当前时间，说明在期望的时间没有请求到达，
        // 说明没有按照期望消耗令牌，故本次请求直接通过，并更新上次通过的时间为当前时间
        if (expectedTime <= currentTime) {
            // Contention may exist here, but it's okay.
            latestPassedTime.set(currentTime);
            return true;
        } else {
            // 如果 expectedTime 大于当前时间，说明还没到令牌发放时间，当前请求需要等待。首先先计算需要等待是时间，用 waitTime 表示。
            long waitTime = costTime + latestPassedTime.get() - TimeUtil.currentTimeMillis();

            if (waitTime > maxQueueingTimeMs) {
                // 如果计算的需要等待的时间大于允许排队的时间，则返回 false，即本次请求将被限流，返回 FlowException
                return false;
            } else {
                // 进入排队，默认是本次请求通过，故先将上一次通过流量的时间戳增加 costTime
                long oldTime = latestPassedTime.addAndGet(costTime);
                try {
                    waitTime = oldTime - TimeUtil.currentTimeMillis();
                    if (waitTime > maxQueueingTimeMs) {
                        latestPassedTime.addAndGet(-costTime);
                        return false;
                    }
                    // in race condition waitTime may <= 0
                    if (waitTime > 0) {
                        // 将当前请求先阻塞一会，waitTime后就可以通过了
                        Thread.sleep(waitTime);
                    }
                    return true;
                } catch (InterruptedException e) {
                }
            }
        }
        return false;
    }

}
