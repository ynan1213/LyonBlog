package com.epichust.main;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Test02
{
    public static void main(String[] args) throws Exception
    {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(3);

        // 只对 app1 来源的做限流
        //rule.setLimitApp("dubbo");

        //rule.setRefResource("xxx");
        //rule.setStrategy(RuleConstant.STRATEGY_RELATE);

        // 配置流控效果，立即拒绝/WarmUp/匀速排队
        //rule.setControlBehavior();
        // 匀速排队模式下的最长等待时间，超时也会抛出异常
        //rule.setMaxQueueingTimeMs();

        // 集群限流
        //rule.setClusterMode(true);

        rules.add(rule);
        FlowRuleManager.loadRules(rules);

        // 如果这里配置 origin 为 dubbo，是不会做限流的
        //ContextUtil.enter("context-01", "dubbo");
        ContextUtil.enter("context-01");

        while (true)
        {
            try (Entry entry = SphU.entry("HelloWorld"))
            {
                System.out.println("hello world");
            } catch (BlockException ex)
            {
                System.out.println("系统异常!");
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}
