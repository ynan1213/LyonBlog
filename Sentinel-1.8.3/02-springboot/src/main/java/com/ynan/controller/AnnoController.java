package com.ynan.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuannan
 * @Date 2022/4/14 00:08
 */
@RestController
public class AnnoController {

    @PostConstruct
    public void initFlowRules() {
        List<FlowRule> ruleList = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("anno_resource");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(2);
        ruleList.add(rule);
        FlowRuleManager.loadRules(ruleList);
    }

    /**
     * value值就是resourceName，如果没有配置，默认取【全限定类名:方法名】例如：com.ynan.controller.AnnoController:world()
     */
    @SentinelResource(value = "anno_resource", blockHandler = "worldBlockHandler", fallback = "worldFallback")
    @RequestMapping("/world")
    public String world() throws FlowException {
        if (1 == 1) {
            throw new RuntimeException("xxx");
        }
        //        if (1 == 1) {
        //            throw new FlowException("xxx");
        //        }
        return "hello world 222";
    }

    /**
     * 当目标方法发生BlockException异常时会执行该方法，如果未配置blockHandlerClass：
     * 1、该方法必须和目标方法在同一个类；
     * 2、和目标方法返回值类型必须相同，public修饰，可为或可不为static；
     * 3、参数个数和类型相同，必须比目标方法多一个参数，类型是 BlockException，放在参数最后一位；
     */
    public String worldBlockHandler(BlockException e) {
        return "注解方式限流：world ！！！: " + e.getMessage();
    }

    /**
     *
     *
     */
    public String worldFallback(Throwable e) {
        return "目标方法发生异常：world ！！！: " + e.getMessage();
    }
}
