package com.ynan.component;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2021/11/13 10:57
 */
//@Component
public class SmartLifecycleService implements SmartLifecycle {

	@Override
	public boolean isAutoStartup() {
		System.out.println("SmartLifecycleService isAutoStartup");
		return true;
	}

	@Override
	public void stop(Runnable callback) {
		System.out.println("SmartLifecycleService stop(Runnable callback)");
	}

	@Override
	public int getPhase() {
		return 0;
	}

	@Override
	public void start() {
		System.out.println("SmartLifecycleService start");
	}

	@Override
	public void stop() {
		System.out.println("SmartLifecycleService stop");
	}

	@Override
	public boolean isRunning() {
		return false;
	}
}
