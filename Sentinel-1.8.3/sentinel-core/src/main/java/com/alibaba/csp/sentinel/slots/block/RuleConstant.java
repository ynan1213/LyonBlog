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
package com.alibaba.csp.sentinel.slots.block;

import com.alibaba.csp.sentinel.node.IntervalProperty;

/**
 * @author youji.zj
 * @author jialiang.linjl
 */
public final class RuleConstant {

    public static final int FLOW_GRADE_THREAD = 0;
    public static final int FLOW_GRADE_QPS = 1;

    public static final int DEGRADE_GRADE_RT = 0;
    public static final int DEGRADE_GRADE_EXCEPTION_RATIO = 1;
    public static final int DEGRADE_GRADE_EXCEPTION_COUNT = 2;

    public static final int DEGRADE_DEFAULT_SLOW_REQUEST_AMOUNT = 5;
    public static final int DEGRADE_DEFAULT_MIN_REQUEST_AMOUNT = 5;

    public static final int AUTHORITY_WHITE = 0;
    public static final int AUTHORITY_BLACK = 1;

    /**
     * 1. 直接STRATEGY_DIRECT（默认）：接口达到限流条件时，开启限流。
     *
     * 2. 关联STRATEGY_RELATE（关联）：当关联的资源达到限流条件时，开启限流，适合做应用让步。
     *    例如为一个查询的接口添加关联流控，关联资源为一个更新的接口，当更新的接口达到阈值时，开启查询接口的限流，
     *    为更新接口让步服务器资源。例如，设置了rule.setResource("app1")，rule.setRefResource("app2")进行统计的时候是对资源app2进行统计。
     *
     * 3. 链路STRATEGY_CHAIN（链式）：当从某个接口过来的资源达到限流条件时，开启限流。
     *
     *         	          machine-root
     *                        /       \
     *                       /         \
     *                 Entrance1     Entrance2
     *                    /             \
     *                   /               \
     *          DefaultNode(nodeA)   DefaultNode(nodeA)
     *
     *    上图中来自入口 `Entrance1` 和 `Entrance2` 的请求都调用到了资源 `NodeA`，Sentinel 允许只根据某个入口的统计信息对资源限流。
     *    比如我们可以设置 `strategy` 为 `RuleConstant.STRATEGY_CHAIN`，同时设置 `refResource` 为 `Entrance1` 来表示只有从入口 `Entrance1`
     *    的调用才会记录到 `NodeA` 的限流统计当中，而不关心经 `Entrance2` 到来的调用。
     */
    public static final int STRATEGY_DIRECT = 0;
    public static final int STRATEGY_RELATE = 1;
    public static final int STRATEGY_CHAIN = 2;

    public static final int CONTROL_BEHAVIOR_DEFAULT = 0;
    public static final int CONTROL_BEHAVIOR_WARM_UP = 1;
    public static final int CONTROL_BEHAVIOR_RATE_LIMITER = 2;
    public static final int CONTROL_BEHAVIOR_WARM_UP_RATE_LIMITER = 3;

    public static final int DEFAULT_BLOCK_STRATEGY = 0;
    public static final int TRY_AGAIN_BLOCK_STRATEGY = 1;
    public static final int TRY_UNTIL_SUCCESS_BLOCK_STRATEGY = 2;

    public static final int DEFAULT_RESOURCE_TIMEOUT_STRATEGY = 0;
    public static final int RELEASE_RESOURCE_TIMEOUT_STRATEGY = 1;
    public static final int KEEP_RESOURCE_TIMEOUT_STRATEGY = 2;

    public static final String LIMIT_APP_DEFAULT = "default";
    public static final String LIMIT_APP_OTHER = "other";

    public static final int DEFAULT_SAMPLE_COUNT = 2;
    public static final int DEFAULT_WINDOW_INTERVAL_MS = 1000;

    private RuleConstant() {}
}
