package com.ynan.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener1
{

	@EventListener(MyEvent.class)
	public void xxx(MyEvent event)
	{
		System.out.println("方式二：@EventListener注解  监听到MyEvent事件.....");
		event.print();
	}

	@EventListener(MyEvent.class)
	public void yyy(MyEvent event)
	{
		System.out.println("方式二（可以多个）：@EventListener注解  监听到MyEvent事件.....");
		event.print();
	}

}
