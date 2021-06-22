package com.ynan.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener implements ApplicationListener<MyEvent>
{
	@Override
	public void onApplicationEvent(MyEvent event)
	{
		System.out.println("方式一：实现ApplicationListener  监听到MyEvent事件.....");
		event.print();
	}


}
