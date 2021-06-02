package com.epichust.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener implements ApplicationListener<MyEvent>
{
	@Override
	public void onApplicationEvent(MyEvent event)
	{
		System.out.println("实现ApplicationListener  监听到MyEvent事件.....");
		event.print();
	}

	@EventListener(MyEvent.class)
	public void xxx(MyEvent event)
	{
		System.out.println("@EventListener方式  监听到MyEvent事件.....");
		event.print();
	}

	@EventListener(MyEvent.class)
	public void yyy(MyEvent event)
	{
		System.out.println("@EventListener方式-1  监听到MyEvent事件.....");
		event.print();
	}

}
