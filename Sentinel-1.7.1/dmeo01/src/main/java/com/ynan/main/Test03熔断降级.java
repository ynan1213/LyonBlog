package com.ynan.main;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author yn
 * @create 2021/7/10 17:37
 * @description
 */
public class Test03熔断降级 {

    public static void main(String[] args) throws Exception {
        List<DegradeRule> rules = new ArrayList<>();

        DegradeRule rule = new DegradeRule();
        rule.setResource("HelloWorld");

        // 三种模式可选：平均响应时间、异常比例、异常数
        //rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);

        // 慢调用比例模式下为慢调用临界 RT（超出该值计为慢调用）
        // 异常比例/异常数模式下为对应的阈值
        rule.setCount(2);

        rule.setMinRequestAmount(10);

        // 熔断时长，单位为 s
        rule.setTimeWindow(10);

        rules.add(rule);
        DegradeRuleManager.loadRules(rules);

        ContextUtil.enter("context-01", "appA");

        while (true) {
            try (Entry entry = SphU.entry("HelloWorld")) {
                System.out.println("hello world");
            } catch (FlowException e) {
                System.out.println("发生了限流控制：" + e.getMessage());
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }
    }
}
