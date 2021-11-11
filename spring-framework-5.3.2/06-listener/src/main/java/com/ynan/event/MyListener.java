package com.ynan.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 实现方式一：实现ApplicationListener接口
 */
@Component
public class MyListener implements ApplicationListener<MyEvent> {

	@Override
	public void onApplicationEvent(MyEvent event) {
		System.out.println("线程ID：" + Thread.currentThread().getName());
		System.out.println("方式一：实现ApplicationListener  监听到MyEvent事件.....");
		event.print();
	}
}
