package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class test2 {
	private Lock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		
	}
	
	public void insert(Thread thread) throws InterruptedException {
		lock.lockInterruptibly();
		try {
			System.out.println(thread.getName()+"得到了锁");
			System.currentTimeMillis();
		}finally {
			
		}
	}
}
