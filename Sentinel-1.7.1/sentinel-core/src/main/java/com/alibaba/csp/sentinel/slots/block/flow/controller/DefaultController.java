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
import com.alibaba.csp.sentinel.node.OccupyTimeoutProperty;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.PriorityWaitException;
import com.alibaba.csp.sentinel.slots.block.flow.TrafficShapingController;
import com.alibaba.csp.sentinel.util.TimeUtil;

/**
 * Default throttling controller (immediately reject strategy).
 *
 * @author jialiang.linjl
 * @author Eric Zhao
 */
public class DefaultController implements TrafficShapingController {

    private static final int DEFAULT_AVG_USED_TOKENS = 0;

    // 就是 FlowRule 的 count
    private double count;
    // 就是 FlowRule 的 grade
    private int grade;

    public DefaultController(double count, int grade) {
        this.count = count;
        this.grade = grade;
    }

    @Override
    public boolean canPass(Node node, int acquireCount) {
        return canPass(node, acquireCount, false);
    }

    @Override
    public boolean canPass(Node node, int acquireCount, boolean prioritized) {
        // 当前时间窗口内已创建的线程数量(FLOW_GRADE_THREAD) 或 已通过的请求个数(FLOW_GRADE_QPS)
        int curCount = avgUsedTokens(node);
        if (curCount + acquireCount > count) {
            // 如果该请求存在优先级，即 prioritized 设置为 true，并且流控类型为基于QPS进行限流，则进入if，否则直接返回false
            if (prioritized && grade == RuleConstant.FLOW_GRADE_QPS) {
                long currentTime;
                long waitInMs;
                currentTime = TimeUtil.currentTimeMillis();

                // 尝试抢占下一个滑动窗口的令牌，并返回该时间窗口所剩余的时间
                // 如果获取失败，则返回 OccupyTimeoutProperty.getOccupyTimeout() 值，该返回值的作用就是当前申请资源的线程将 sleep(阻塞)的时间
                waitInMs = node.tryOccupyNext(currentTime, acquireCount, count);

                // 如果 waitInMs 小于抢占的最大超时时间，则在下一个时间窗口中增加对应令牌数，并且线程将sleep
                if (waitInMs < OccupyTimeoutProperty.getOccupyTimeout()) {
                    node.addWaitingRequest(currentTime + waitInMs, acquireCount);
                    node.addOccupiedPass(acquireCount);
                    sleep(waitInMs);

                    // PriorityWaitException indicates that the request will pass after waiting for {@link @waitInMs}.
                    // 这里不是很明白为什么等待 waitMs 之后，还需要抛出 PriorityWaitException，那这个prioritized 机制、
                    // 可抢占下一个时间窗口的令牌有什么意义呢？应该是一个BUG吧
                    throw new PriorityWaitException(waitInMs);
                }
            }
            // 否则直接返回 false，最终会直接抛出 FlowException，即快速失败
            return false;
        }
        return true;
    }

    private int avgUsedTokens(Node node) {
        if (node == null) {
            return DEFAULT_AVG_USED_TOKENS;
        }
        return grade == RuleConstant.FLOW_GRADE_THREAD ? node.curThreadNum() : (int) (node.passQps());
    }

    private void sleep(long timeMillis) {
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            // Ignore.
        }
    }
}
