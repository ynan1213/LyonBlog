package com.ynan.main;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.statistic.base.LongAdder;
import com.alibaba.csp.sentinel.util.TimeUtil;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author yn
 * @create 2021/7/10 17:37
 * @description
 */
public class Test02流量控制 {

    public static void main(String[] args) throws Exception {
        /**
         * 1. 同一个资源可以创建多条限流规则。FlowSlot 会对该资源的所有限流规则依次遍历，直到有规则触发限流或者所有规则遍历完毕。
         * 2. 限流的直接表现是在执行的时候抛出 FlowException 异常。FlowException 是 BlockException 的子类
         */
        List<FlowRule> rules = new ArrayList<>();

        FlowRule rule = new FlowRule();

        // 资源名，即限流规则的作用对象，对应SphU.entry("HelloWorld")
        rule.setResource("HelloWorld");

        // 限流阈值类型（QPS 或 并发线程数）
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);

        // 限流阈值，3代表每秒最多3次
        rule.setCount(3);

        // 流控针对的调用来源，若为 default 则不区分调用来源，这里appA对应ContextUtil.enter("dubbo1", "appA")
        rule.setLimitApp("appA");

        rule.setStrategy(RuleConstant.STRATEGY_RELATE);

        rules.add(rule);

        FlowRuleManager.loadRules(rules);

        ContextUtil.enter("context-01", "appA");

        try (Entry entry = SphU.entry("HelloWorld")) {
            System.out.println("hello world");
        } catch (FlowException e) {
            System.out.println("发生了限流控制：" + e.getMessage());
        }
    }
}
