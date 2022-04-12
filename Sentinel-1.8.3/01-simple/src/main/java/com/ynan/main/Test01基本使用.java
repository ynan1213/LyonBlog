package com.ynan.main;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author yn
 * @create 2021/7/10 16:20
 * @description
 */
public class Test01基本使用 {

    public static void main(String[] args) {
        Entry entry = null;
        try {
            /**
             * 创建一个Context，Context有如下几个属性：
             *      name：           调用链路入口名称（流控规则中若“流控方式”为“链路”方式，则入口资源名即为上面的 contextName）
             *      origin：         当前调用链的调用源，可为空，对应FlowRule的limitApp
             *      EntranceNode：   当前调用链的入口，创建Context的时候就会初始化，并且会挂到全局ROOT下面，
             *                       每个EntranceNode都有一个id，就是上面contextName，如果未手动指定context，则为sentinel_default_context
             *      curEntry：       当前调用链的当前entry
             *
             * 要调用 SphU.entry 必须要有一个 Context，如果这里没有手动指定，里面还是会通过 sentinel_default_context 创建一个Context
             * 也就是说每个线程必须绑定一个 Context，因为有全局 contextNameNodeMap（key为 context name，value为 EntranceNode）缓存的存在
             * 只要 context name相同，即使不同线程创建不同的 Context，但是Context 内部的 EntranceNode 是同一个
             *
             * name 代表什么呢？就把它理解成项目上下文，创建EntranceNode时name作为构造参数
             * origin 代表着调用方标识，比如pc端、app端、pda端等
             */
//            ContextUtil.enter("mvc");
            Context context = ContextUtil.enter("mvc", "appA");

            /**
             *  A 代表什么呢？ 内部会将 A 封装为 ResourceWrapper，也就是封装为一个资源
             *  所以A代表的应该是一个资源，什么是资源呢？就是这句代码后面要执行的代码或者是一个方法
             */
            entry = SphU.entry("A");
            entry = SphU.entry("A");
            entry = SphU.entry("A");
            ContextUtil.exit();
        } catch (BlockException e) {
            System.out.println("发生了BlockException异常：" + e.getMessage());
        } catch (Exception e) {
            System.out.println("发生了Exception异常：" + e.getMessage());
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
    }
}
