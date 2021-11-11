package com.ynan.event;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 方式三：异步
 */
@Component
public class MyListener2 implements ApplicationListener<MyEvent> {

	@Override
	@Async
	public void onApplicationEvent(MyEvent event) {
		System.out.println("线程ID：" + Thread.currentThread().getName());
		System.out.println("方式三： 异步监听到MyEvent事件.....");
		event.print();
	}
}
