package com.ynan.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/2/12 20:43
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        try (Entry entry = SphU.entry("HelloWorld")) {
            return "hello world !!!!!!";
        } catch (BlockException e) {
            return "发生了限流控制：" + e.getMessage();
        }
    }

//    @PostConstruct
//    public void initFlowRules() {
//        List<FlowRule> ruleList = new ArrayList<>();
//        FlowRule rule = new FlowRule();
//        rule.setResource("HelloWorld");
//        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rule.setCount(2);
//        ruleList.add(rule);
//        FlowRuleManager.loadRules(ruleList);
//    }

    @RequestMapping("/world")
    @SentinelResource(value = "World_anno", blockHandler = "worldBlockHandler")
    public String world(){
        if(1 == 1) throw new RuntimeException("xxxxx");
        return "hello world 222";
    }

    public String worldBlockHandler(BlockException e){
        return "注解方式限流：world ！！！: " + e.getMessage();
    }
}
