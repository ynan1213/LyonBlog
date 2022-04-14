package com.ynan.main;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import java.util.Collections;

/**
 * @Author yuannan
 * @Date 2022/4/13 23:43
 */
public class Test04热点参数 {

    public static void main(String[] args) throws BlockException {
        ParamFlowRule rule = new ParamFlowRule("HelloWorld")
            .setParamIdx(0)
            .setCount(5);
        // 针对 int 类型的参数 PARAM_B，单独设置限流 QPS 阈值为 10，而不是全局的阈值 5.
        ParamFlowItem item = new ParamFlowItem().setObject(String.valueOf("paramA"))
            .setClassType(int.class.getName())
            .setCount(10);
        rule.setParamFlowItemList(Collections.singletonList(item));
        ParamFlowRuleManager.loadRules(Collections.singletonList(rule));

        ContextUtil.enter("context-01", "appA");

        Entry entry = null;
        try {
            entry = SphU.entry("HelloWorld", EntryType.IN, 1, "paramA");
            System.out.println("hello world");
        } catch (FlowException e) {
            System.out.println("发生了限流控制：" + e.getMessage());
        } finally {
            entry.exit();
        }
    }
}
