package com.epichust.listener;

import org.springframework.context.ApplicationEvent;

public class MyApplicationEvent extends ApplicationEvent
{
    public MyApplicationEvent(Object source)
    {
        super(source);
    }

    public void printMsg(String msg)
    {
        System.out.println("监听到事件：" + MyApplicationEvent.class);
    }

}
