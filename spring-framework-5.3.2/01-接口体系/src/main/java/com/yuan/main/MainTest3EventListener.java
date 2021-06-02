package com.epichust.main;

import com.epichust.config.RootConfig;
import com.epichust.event.MyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest3EventListener
{
	public static void main(String[] args)
	{
		ApplicationContext ac = new AnnotationConfigApplicationContext(RootConfig.class);
		ac.publishEvent(new MyEvent("null", "MyEvent 事件发布"));
	}
}
