package com.sqsoft.thread;

import java.util.concurrent.TimeUnit;

/**
 * 测试并发问题
 *
 * @author yuannan
 */

public class ThreadDemo implements Runnable{
	
	static volatile int i;
	
	@Override
	public void run() {
		for(int i=1; i<=10000; i++) {
			add();
		}
	}

	private void add() {
		i++;
	}
	
	public static void main(String[] args) throws InterruptedException {
		ThreadDemo td = new ThreadDemo();
		Thread[] threads = new Thread[10];
		for(int i=0; i<10; i++) {
			threads[i] = new Thread(td);
			threads[i].start();
		}
		for(int i=0; i<10; i++) {
			threads[i].join();
		}
		System.out.println(i);
		TimeUnit.DAYS.sleep(1);
	}
	
}
