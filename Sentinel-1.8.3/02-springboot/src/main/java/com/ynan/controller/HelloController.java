package com.ynan.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/2/12 20:43
 */
@RestController
public class HelloController {

    @PostConstruct
    public void initFlowRules() {
//        List<FlowRule> ruleList = new ArrayList<>();
//        FlowRule rule = new FlowRule();
//        rule.setResource("local_rule_hello");
//        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule.setCount(2);
//        ruleList.add(rule);
//        FlowRuleManager.loadRules(ruleList);

        // 慢调用比例
//        List<DegradeRule> ruleList = new ArrayList<>();
//        DegradeRule rule = new DegradeRule();
//        rule.setResource("local_rule_hello");
//        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
//        rule.setCount(2000);
//        rule.setMinRequestAmount(6);
//        rule.setSlowRatioThreshold(0.8d);
//        rule.setTimeWindow(10);
//        rule.setStatIntervalMs(1000);
//        ruleList.add(rule);
//        DegradeRuleManager.loadRules(ruleList);

        // 异常比例
//        List<DegradeRule> ruleList = new ArrayList<>();
//        DegradeRule rule = new DegradeRule();
//        rule.setResource("local_rule_hello");
//        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);
//        rule.setCount(0.8); // 在当前策略下，count必须 <= 1
//        rule.setTimeWindow(10);
//        ruleList.add(rule);
//        DegradeRuleManager.loadRules(ruleList);

        // 异常数
//        List<DegradeRule> ruleList = new ArrayList<>();
//        DegradeRule rule = new DegradeRule();
//        rule.setResource("local_rule_hello");
//        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
//        rule.setCount(3);
//        rule.setTimeWindow(10);
//        ruleList.add(rule);
//        DegradeRuleManager.loadRules(ruleList);
    }

    @RequestMapping("/hello")
    public String hello() {
        ContextUtil.enter("spring_mvc", "PC");
        Entry entry = null;
        try {
            entry = SphU.entry("local_rule_hello");
            System.out.println("thread :" + Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(3);
            return "hello world !!!!!!";
        } catch (BlockException | InterruptedException e) {
            return "发生了降级：" + e.getMessage();
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }
}
