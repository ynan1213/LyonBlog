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
package com.alibaba.csp.sentinel.slots.system;

import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.node.DefaultNode;
import com.alibaba.csp.sentinel.slotchain.AbstractLinkedProcessorSlot;
import com.alibaba.csp.sentinel.slotchain.ProcessorSlot;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;

/**
 * A {@link ProcessorSlot} that dedicates to {@link SystemRule} checking.
 *
 * 系统自适应限流
 * 这个 slot 会根据对于当前系统的整体情况，对入口资源的调用进行动态调配。其原理是让入口的流量和当前系统的预计容量达到一个动态平衡。
 * 注意系统规则只对入口流量起作用（调用类型为 EntryType.IN），对出口流量无效。可通过 SphU.entry(res, entryType) 指定调用类型，
 * 如果不指定，默认是EntryType.OUT。
 *
 * EntryType.IN 代表这个是入口流量，比如我们的接口对外提供服务，那么我们通常就是控制入口流量；
 * EntryType.OUT 代表出口流量，比如上面的 getOrderInfo 方法（没写默认就是 OUT），它的业务需要调用订单服务，
 * 像这种情况，压力其实都在订单服务中，那么我们就指定它为出口流量。这个流量类型有什么用呢？
 * 答案在 SystemSlot 类中，它用于实现自适应限流，根据系统健康状态来判断是否要限流，如果是 OUT 类型，由于压力在外部系统中，所以就不需要执行这个规则
 */
public class SystemSlot extends AbstractLinkedProcessorSlot<DefaultNode>
{
    @Override
    public void entry(Context context, ResourceWrapper resourceWrapper, DefaultNode node, int count, boolean prioritized, Object... args) throws Throwable
    {
        // 当前的统计值和系统配置的进行比较，各个维度超过范围抛BlockException
        SystemRuleManager.checkSystem(resourceWrapper);
        fireEntry(context, resourceWrapper, node, count, prioritized, args);
    }

    @Override
    public void exit(Context context, ResourceWrapper resourceWrapper, int count, Object... args)
    {
        fireExit(context, resourceWrapper, count, args);
    }

}
