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
package com.alibaba.csp.sentinel.slots.statistic;

import java.util.Collection;

import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.slotchain.ProcessorSlotEntryCallback;
import com.alibaba.csp.sentinel.slotchain.ProcessorSlotExitCallback;
import com.alibaba.csp.sentinel.slots.block.flow.PriorityWaitException;
import com.alibaba.csp.sentinel.util.TimeUtil;
import com.alibaba.csp.sentinel.Constants;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.node.ClusterNode;
import com.alibaba.csp.sentinel.node.DefaultNode;
import com.alibaba.csp.sentinel.slotchain.AbstractLinkedProcessorSlot;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * <p>
 * A processor slot that dedicates to real time statistics.
 * When entering this slot, we need to separately count the following
 * information:
 * <ul>
 * <li>{@link ClusterNode}: total statistics of a cluster node of the resource ID.</li>
 * <li>Origin node: statistics of a cluster node from different callers/origins.</li>
 * <li>{@link DefaultNode}: statistics for specific resource name in the specific context.</li>
 * <li>Finally, the sum statistics of all entrances.</li>
 * </ul>
 * </p>
 *
 * @author jialiang.linjl
 * @author Eric Zhao
 */
public class StatisticSlot extends AbstractLinkedProcessorSlot<DefaultNode>
{

    @Override
    public void entry(Context context, ResourceWrapper resourceWrapper, DefaultNode node, int count, boolean prioritized, Object... args) throws Throwable
    {
        try
        {
            fireEntry(context, resourceWrapper, node, count, prioritized, args);

            // 将正在执行线程数统计指标加一
            node.increaseThreadNum();
            // 并将通过的请求数量指标增加对应的值
            node.addPassRequest(count);

            // 如果上下文环境中保存了调用的源头（调用方）的节点信息不为空，则更新该节点的统计数据：线程数与通过数量。
            if (context.getCurEntry().getOriginNode() != null)
            {
                // Add count for origin node.
                context.getCurEntry().getOriginNode().increaseThreadNum();
                context.getCurEntry().getOriginNode().addPassRequest(count);
            }

            // 如果资源的进入类型为 EntryType.IN，表示入站流量，更新入站全局统计数据(集群范围 ClusterNode)。
            if (resourceWrapper.getEntryType() == EntryType.IN)
            {
                // Add count for global inbound entry node for global statistics.
                Constants.ENTRY_NODE.increaseThreadNum();
                Constants.ENTRY_NODE.addPassRequest(count);
            }

            // 执行注册的进入Handler，可以通过 StatisticSlotCallbackRegistry 的 addEntryCallback 注册相关监听器
            for (ProcessorSlotEntryCallback<DefaultNode> handler : StatisticSlotCallbackRegistry.getEntryCallbacks())
            {
                handler.onPass(context, resourceWrapper, node, count, args);
            }
        } catch (PriorityWaitException ex)
        {
            node.increaseThreadNum();
            if (context.getCurEntry().getOriginNode() != null)
            {
                // Add count for origin node.
                context.getCurEntry().getOriginNode().increaseThreadNum();
            }

            if (resourceWrapper.getEntryType() == EntryType.IN)
            {
                // Add count for global inbound entry node for global statistics.
                Constants.ENTRY_NODE.increaseThreadNum();
            }
            // Handle pass event with registered entry callback handlers.
            for (ProcessorSlotEntryCallback<DefaultNode> handler : StatisticSlotCallbackRegistry.getEntryCallbacks())
            {
                handler.onPass(context, resourceWrapper, node, count, args);
            }
        } catch (BlockException e)
        {
            // 如果捕获到 BlockException，则主要增加阻塞的数量
            context.getCurEntry().setError(e);

            // Add block count.
            node.increaseBlockQps(count);
            if (context.getCurEntry().getOriginNode() != null)
            {
                context.getCurEntry().getOriginNode().increaseBlockQps(count);
            }

            if (resourceWrapper.getEntryType() == EntryType.IN)
            {
                // Add count for global inbound entry node for global statistics.
                Constants.ENTRY_NODE.increaseBlockQps(count);
            }

            // Handle block event with registered entry callback handlers.
            for (ProcessorSlotEntryCallback<DefaultNode> handler : StatisticSlotCallbackRegistry.getEntryCallbacks())
            {
                handler.onBlocked(e, context, resourceWrapper, node, count, args);
            }

            throw e;
        } catch (Throwable e)
        {
            // 如果是系统异常，则增加异常数量。
            context.getCurEntry().setError(e);

            // This should not happen.
            node.increaseExceptionQps(count);
            if (context.getCurEntry().getOriginNode() != null)
            {
                context.getCurEntry().getOriginNode().increaseExceptionQps(count);
            }

            if (resourceWrapper.getEntryType() == EntryType.IN)
            {
                Constants.ENTRY_NODE.increaseExceptionQps(count);
            }
            throw e;
        }
    }

    @Override
    public void exit(Context context, ResourceWrapper resourceWrapper, int count, Object... args)
    {
        DefaultNode node = (DefaultNode) context.getCurNode();

        // 成功执行
        if (context.getCurEntry().getError() == null)
        {
            // 计算本次响应时间，将本次响应时间收集到 Node 中
            long rt = TimeUtil.currentTimeMillis() - context.getCurEntry().getCreateTime();
            int maxStatisticRt = SentinelConfig.statisticMaxRt();
            if (rt > maxStatisticRt)
            {
                rt = maxStatisticRt;
            }

            // Record response time and success count.
            node.addRtAndSuccess(rt, count);
            if (context.getCurEntry().getOriginNode() != null)
            {
                context.getCurEntry().getOriginNode().addRtAndSuccess(rt, count);
            }

            // 将当前活跃线程数减一
            node.decreaseThreadNum();

            if (context.getCurEntry().getOriginNode() != null)
            {
                context.getCurEntry().getOriginNode().decreaseThreadNum();
            }

            if (resourceWrapper.getEntryType() == EntryType.IN)
            {
                Constants.ENTRY_NODE.addRtAndSuccess(rt, count);
                Constants.ENTRY_NODE.decreaseThreadNum();
            }
        } else
        {
            // Error may happen.
        }

        // Handle exit event with registered exit callback handlers.
        Collection<ProcessorSlotExitCallback> exitCallbacks = StatisticSlotCallbackRegistry.getExitCallbacks();
        for (ProcessorSlotExitCallback handler : exitCallbacks)
        {
            handler.onExit(context, resourceWrapper, count, args);
        }

        fireExit(context, resourceWrapper, count);
    }
}
