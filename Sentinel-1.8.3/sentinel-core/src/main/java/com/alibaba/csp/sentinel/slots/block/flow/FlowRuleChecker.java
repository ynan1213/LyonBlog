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
package com.alibaba.csp.sentinel.slots.block.flow;

import java.util.Collection;

import com.alibaba.csp.sentinel.cluster.ClusterStateManager;
import com.alibaba.csp.sentinel.cluster.server.EmbeddedClusterTokenServerProvider;
import com.alibaba.csp.sentinel.cluster.client.TokenClientProvider;
import com.alibaba.csp.sentinel.cluster.TokenResultStatus;
import com.alibaba.csp.sentinel.cluster.TokenResult;
import com.alibaba.csp.sentinel.cluster.TokenService;
import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.node.DefaultNode;
import com.alibaba.csp.sentinel.node.Node;
import com.alibaba.csp.sentinel.slotchain.ResourceWrapper;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.clusterbuilder.ClusterBuilderSlot;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.csp.sentinel.util.function.Function;

/**
 * Rule checker for flow control rules.
 *
 * @author Eric Zhao
 */
public class FlowRuleChecker {

    public void checkFlow(Function<String, Collection<FlowRule>> ruleProvider, ResourceWrapper resource,
                          Context context, DefaultNode node, int count, boolean prioritized) throws BlockException {
        if (ruleProvider == null || resource == null) {
            return;
        }
        // 根据资源名称获取流控规则，内部实际是 FlowRuleManager.getFlowRuleMap()
        // 同一个资源可以创建多条限流规则，对该资源的所有限流规则依次遍历，有一条不通过则抛异常
        Collection<FlowRule> rules = ruleProvider.apply(resource.getName());
        if (rules != null) {
            for (FlowRule rule : rules) {
                if (!canPassCheck(rule, context, node, count, prioritized)) {
                    throw new FlowException(rule.getLimitApp(), rule);
                }
            }
        }
    }

    public boolean canPassCheck(/*@NonNull*/ FlowRule rule, Context context, DefaultNode node,
                                                    int acquireCount) {
        return canPassCheck(rule, context, node, acquireCount, false);
    }

    public boolean canPassCheck(/*@NonNull*/ FlowRule rule, Context context, DefaultNode node, int acquireCount,
                                                    boolean prioritized) {
        /**
         * limitApp是页面上的来源应用，默认是default，表示接收所有的应用，
         * 这里如果为空则默认通过，因为代码中约定了default是代表所有应用，所以空值为非法值，
         * 这里为什么不把default或者空值当做代表所有应用的限流，可能是因为空值还包括规则字段丢失的情况，应该算作异常情况
         */
        String limitApp = rule.getLimitApp();
        if (limitApp == null) {
            return true;
        }
        // 集群模式
        if (rule.isClusterMode()) {
            return passClusterCheck(rule, context, node, acquireCount, prioritized);
        }
        // 单机模式
        return passLocalCheck(rule, context, node, acquireCount, prioritized);
    }

    private static boolean passLocalCheck(FlowRule rule, Context context, DefaultNode node, int acquireCount,
                                          boolean prioritized) {
        // 根据不同情况选择不同Node
        Node selectedNode = selectNodeByRequesterAndStrategy(rule, context, node);
        // 没有匹配，表示不做限流
        if (selectedNode == null) {
            return true;
        }

        return rule.getRater().canPass(selectedNode, acquireCount, prioritized);
    }

    static Node selectReferenceNode(FlowRule rule, Context context, DefaultNode node) {
        String refResource = rule.getRefResource();
        int strategy = rule.getStrategy();

        if (StringUtil.isEmpty(refResource)) {
            return null;
        }

        // 如果流控模式为 RuleConstant.STRATEGY_RELATE(关联)，则从集群环境中获取对应关联资源所代表的 Node，
        // ClusterBuilderSlot会收集每一个资源的实时统计信息
        if (strategy == RuleConstant.STRATEGY_RELATE) {
            return ClusterBuilderSlot.getClusterNode(refResource);
        }

        // 如果流控模式为 RuleConstant.STRATEGY_CHAIN(链路)，则判断当前调用上下文的入口资源与规则配置的是否一样，
        // 如果是，则返回入口资源对应的 Node，否则返回 null，注意：返回空则该条流控规则直接通过。
        if (strategy == RuleConstant.STRATEGY_CHAIN) {
            if (!refResource.equals(context.getName())) {
                return null;
            }
            return node;
        }
        // No node.
        return null;
    }

    private static boolean filterOrigin(String origin) {
        // Origin cannot be `default` or `other`.
        return !RuleConstant.LIMIT_APP_DEFAULT.equals(origin) && !RuleConstant.LIMIT_APP_OTHER.equals(origin);
    }

    static Node selectNodeByRequesterAndStrategy(/*@NonNull*/ FlowRule rule, Context context, DefaultNode node) {
        // The limit app should not be empty.
        /**
         * limitApp到这里了，不会为空，它的作用：
         * 1. default：表示不区分调用者，来自任何调用者的请求都将进行限流统计。如果这个资源名的调用总和超过了这条规则定义的阈值，则触发限流。
         * 2. {some_origin_name}：表示针对特定的调用者，只有来自这个调用者的请求才会进行流量控制。例如 NodeA 配置了一条针对调用者caller1的规则，
         *    那么当且仅当来自 caller1 对 NodeA 的请求才会触发流量控制。
         * 3. other：表示针对除 {some_origin_name} 以外的其余调用方的流量进行流量控制。例如，资源NodeA配置了一条针对调用者 caller1 的限流规则，
         *    同时又配置了一条调用者为 other 的规则，那么任意来自非 caller1 对 NodeA 的调用，都不能超过 other 这条规则定义的阈值。
         */
        // 该条限流规则针对的调用方
        String limitApp = rule.getLimitApp();
        int strategy = rule.getStrategy();
        // 本次请求的调用方，从当前上下文环境中获取，例如 dubbo 服务提供者，原始调用方为 dubbo 服务提供者的 application。
        String origin = context.getOrigin();

        // 情形①：limitApp 等于 origin，并且不等于default和other，并且表示针对特定的调用者，进入if
        if (limitApp.equals(origin) && filterOrigin(origin)) {
            /**
             * 如果策略是STRATEGY_DIRECT(调用方限流），则限流节点是originNode；
             * 如果限流策略是STRATEGY_RELATE(关联限流），则限流节点是refResource的clusterNode；
             * 如果限流策略是STRATEGY_CHAIN(链路限流），并且refResource等于contextName，则限流节点就是node。
             */
            if (strategy == RuleConstant.STRATEGY_DIRECT) {
                // Matches limit origin, return origin statistic node.
                return context.getOriginNode();
            }

            return selectReferenceNode(rule, context, node);

            // 情形②：limitApp 等于 default，表示不区分调用者，来自任何调用者的请求都将进行限流统计。进入if
        } else if (RuleConstant.LIMIT_APP_DEFAULT.equals(limitApp)) {
            if (strategy == RuleConstant.STRATEGY_DIRECT) {
                // Return the cluster node.
                return node.getClusterNode();
            }

            return selectReferenceNode(rule, context, node);


            // 情形③：limitApp 等于 other，
            //        1. origin为空，不会进入else if；
            //        2. 如果origin和任一rule的limitApp的匹配，表示不在other范围，也不会进入else if；
            //        3. 除了1.2，剩下的情况会进入else if
        } else if (RuleConstant.LIMIT_APP_OTHER.equals(limitApp)
            && FlowRuleManager.isOtherOrigin(origin, rule.getResource())) {
            if (strategy == RuleConstant.STRATEGY_DIRECT) {
                return context.getOriginNode();
            }

            return selectReferenceNode(rule, context, node);
        }

        // 其它情况，返回null，表示没有匹配，不做限流判断
        // 情形④：情形①的反例，limitApp 不为default和other，但是不等于 origin，表示没有匹配
        // 情形⑤：情形③的反例，limitApp 等于 other，origin为空或者不在other范围，不做限流
        return null;
    }

    private static boolean passClusterCheck(FlowRule rule, Context context, DefaultNode node, int acquireCount,
                                            boolean prioritized) {
        try {
            // 获取一个 TokenService 服务类。这里实现关键点：
            //      如果当前节点的角色为 CLIENT，返回的 TokenService 为 DefaultClusterTokenClient。
            //      如果当前节点的角色为 SERVER，返回的 TokenService 为 ClusterTokenServer，这里使用了SPI机制，
            //      可以通过查看 META-INF/services 目录下的 com.alibaba.csp.sentinel.cluster.TokenService 文件，默认服务端返回 DefaultTokenService。
            TokenService clusterService = pickClusterService();
            // 如果无法获取到集群限流Token服务，如果该限流规则配置了可以退化为单机限流模式，则退化为单机限流
            if (clusterService == null) {
                return fallbackToLocalOrPass(rule, context, node, acquireCount, prioritized);
            }
            // 获取集群限流的流程ID，该 flowId 全局唯一
            long flowId = rule.getClusterConfig().getFlowId();
            // 通过 TokenService 去申请 token，这里是与单机限流模式最大的差别
            TokenResult result = clusterService.requestToken(flowId, acquireCount, prioritized);
            return applyTokenResult(result, rule, context, node, acquireCount, prioritized);
            // If client is absent, then fallback to local mode.
        } catch (Throwable ex) {
            RecordLog.warn("[FlowRuleChecker] Request cluster token unexpected failed", ex);
        }
        // Fallback to local flow control when token client or server for this rule is not available.
        // If fallback is not enabled, then directly pass.
        return fallbackToLocalOrPass(rule, context, node, acquireCount, prioritized);
    }

    private static boolean fallbackToLocalOrPass(FlowRule rule, Context context, DefaultNode node, int acquireCount,
                                                 boolean prioritized) {
        if (rule.getClusterConfig().isFallbackToLocalWhenFail()) {
            return passLocalCheck(rule, context, node, acquireCount, prioritized);
        } else {
            // The rule won't be activated, just pass.
            return true;
        }
    }

    private static TokenService pickClusterService() {
        if (ClusterStateManager.isClient()) {
            return TokenClientProvider.getClient();
        }
        if (ClusterStateManager.isServer()) {
            return EmbeddedClusterTokenServerProvider.getServer();
        }
        return null;
    }

    private static boolean applyTokenResult(/*@NonNull*/ TokenResult result, FlowRule rule, Context context,
                                                         DefaultNode node,
                                                         int acquireCount, boolean prioritized) {
        switch (result.getStatus()) {
            case TokenResultStatus.OK:
                return true;
            case TokenResultStatus.SHOULD_WAIT:
                // Wait for next tick.
                try {
                    Thread.sleep(result.getWaitInMs());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            case TokenResultStatus.NO_RULE_EXISTS:
            case TokenResultStatus.BAD_REQUEST:
            case TokenResultStatus.FAIL:
            case TokenResultStatus.TOO_MANY_REQUEST:
                return fallbackToLocalOrPass(rule, context, node, acquireCount, prioritized);
            case TokenResultStatus.BLOCKED:
            default:
                return false;
        }
    }
}