package com.ynan.component;

import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2021/11/13 10:38
 */
//@Component
public class LifecycleService implements Lifecycle {

	@Override
	public void start() {
		System.out.println("LifecycleService start ....");
	}

	@Override
	public void stop() {
		System.out.println("LifecycleService stop ....");
	}

	@Override
	public boolean isRunning() {
		return true;
	}
}
