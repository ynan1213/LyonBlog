package com.ynan.main;

import com.ynan.config.RootConfigLis;
import com.ynan.event.MyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class EventListenerMain
{
	public static void main(String[] args)
	{
		ApplicationContext ac = new AnnotationConfigApplicationContext(RootConfigLis.class);
		ac.publishEvent(new MyEvent("null", "MyEvent 事件发布"));
	}
}
