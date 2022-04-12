package com.ynan.whiteAndBlack;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 黑白名单
 */
public class MainTest01
{
    public static void main(String[] args)
    {
        List<AuthorityRule> rules = new ArrayList<>();
        AuthorityRule rule = new AuthorityRule();
        //资源名，即限流规则的作用对象
        rule.setResource("getName");
        // 对应的黑名单/白名单，多个用 , 分隔，如 appA,appB
        rule.setLimitApp("appA,appB");
        // 限制模式，AUTHORITY_WHITE 为白名单模式，AUTHORITY_BLACK 为黑名单模式，默认为白名单模式
        //rule.setStrategy(RuleConstant.AUTHORITY_BLACK);
        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        rules.add(rule);
        AuthorityRuleManager.loadRules(rules);

        ContextUtil.enter("c01", "appC");

        try (Entry entry = SphU.entry("getName"))
        {
            System.out.println("访问成功");
        } catch (BlockException e)
        {
            System.out.println("访问受限");
        }
    }
}
