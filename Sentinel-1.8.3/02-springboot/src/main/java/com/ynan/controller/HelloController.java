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

        List<DegradeRule> ruleList = new ArrayList<>();
        DegradeRule rule = new DegradeRule();
        rule.setResource("local_rule_hello");
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rule.setCount(2000);
        rule.setRtSlowRequestAmount(6);
        rule.setTimeWindow(10);
        ruleList.add(rule);
        DegradeRuleManager.loadRules(ruleList);
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


    @RequestMapping("/world")
    @SentinelResource(value = "World_anno", blockHandler = "worldBlockHandler")
    public String world() {
        //if(1 == 1) throw new RuntimeException("xxxxx");
        return "hello world 222";
    }

    public String worldBlockHandler(BlockException e) {
        return "注解方式限流：world ！！！: " + e.getMessage();
    }
}
