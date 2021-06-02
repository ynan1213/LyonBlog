package com.epichust.main;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.context.Context;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;

public class Test01
{
    public static void main(String[] args)
    {
        try
        {
            Context context = ContextUtil.enter("dubbo1", "appA");
            Entry a = SphU.entry("A");
            a.exit();
            ContextUtil.exit();

            Context context1 = ContextUtil.enter("dubbo2", "appB");
            Entry a1 = SphU.entry("A");
            a.exit();
            ContextUtil.exit();



            Entry b = SphU.entry("B");
            Entry c = SphU.entry("C");
        } catch (BlockException e)
        {
            e.printStackTrace();
            System.out.println("系统异常");
        } catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        try (Entry entry = SphU.entry("HelloWorld")) {
            // Your business logic here.
            System.out.println("hello world");
        } catch (BlockException e) {
            // Handle rejected request.
            e.printStackTrace();
        }

    }
}
