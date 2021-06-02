package com.epichust.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MyApplicationListener implements ApplicationListener<MyApplicationEvent>
{
    @Override
    public void onApplicationEvent(MyApplicationEvent event)
    {
        event.printMsg("自定义监听事件");
        System.out.println("自定义监听器");
    }
}
