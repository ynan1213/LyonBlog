package thread;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test3 {
	
	private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
	
	public static void main(String[] args) {
		final Test3 test = new Test3();
		
		new Thread() {
			public void run() {
				test.get(Thread.currentThread());
			};
		}.start();

		new Thread() {
			public void run() {
				test.get(Thread.currentThread());
			};
		}.start();
	}
	
	public void get(Thread thread) {
		rw.readLock().lock();
		try {
			long start = System.currentTimeMillis();
			while (System.currentTimeMillis() - start <= 1) {
				System.out.println(Thread.currentThread().getName()+"正在进行读操作");
			}
			System.out.println(Thread.currentThread().getName()+"完成读操作=============");
		} finally {
			rw.readLock().unlock();
		}
	}
}
