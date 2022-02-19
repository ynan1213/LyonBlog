package com.ynan.main;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;

/**
 * @author yn
 * @create 2021/7/10 16:20
 * @description
 */
public class Test01基本使用
{
    public static void main(String[] args)
    {
        Entry entry = null;
        try
        {
            /**
             * 创建一个Context，Context有如下几个属性：
             *      name：
             *      origin：         当前调用链的调用源，在限流规则里面会用到
             *      EntranceNode：   当前调用链的入口，创建Context的时候就会初始化，并且会挂到全局 ROOT 下面
             *      curEntry：       当前调用链的当前entry
             *
             * 要调用 SphU.entry 必须要有一个 Context，如果这里没有手动指定，里面还是会通过 sentinel_default_context 创建一个Context
             * 也就是说每个线程必须绑定一个 Context，因为有全局 contextNameNodeMap（key为 context name，value为 EntranceNode）缓存的存在
             * 只要 context name相同，即使不同线程创建不同的 Context，但是Context 内部的 EntranceNode 是同一个
             *
             * name 代表什么呢？就把它理解成项目调用环境，比如 RPC、dubbo 或者 http，创建EntranceNode时name作为构造参数
             */
            Context context = ContextUtil.enter("dubbo1", "appA");

            /**
             *  A 代表什么呢？ 内部会将 A 封装为 ResourceWrapper，也就是封装为一个资源
             *  所以A代表的应该是一个资源，什么是资源呢？就是这句代码后面要执行的代码或者是一个方法
             */
            entry = SphU.entry("A");
            entry = SphU.entry("B");
            entry = SphU.entry("C");
            ContextUtil.exit();
        } catch (BlockException e)
        {
            System.out.println("发生了BlockException异常：" + e.getMessage());
        } catch (Exception e)
        {
            System.out.println("发生了Exception异常：" + e.getMessage());
        } finally
        {
            if (entry != null)
            {
                entry.exit();
            }
        }
    }
}
