package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {
	private List<Integer> list = new ArrayList<Integer>();
	private Lock lock = new ReentrantLock();
	public static void main(String[] args) {
		final Test1 test = new Test1();
		
		new Thread() {
			public void run() {
				test.insert(Thread.currentThread());
			};
		}.start();
		
		new Thread() {
			public void run() {
				test.insert(Thread.currentThread());
			};
		}.start();
		
	}
	
	public void insert(Thread thread) {
//		Lock lock = new ReentrantLock();
//		lock.lock();
		if(lock.tryLock()) {
			try {
				System.out.println(thread.getName()+"得到了锁");
				for(int i=0; i<10000; i++) {
					list.add(i);
				}
			}catch(Exception e){
				
			}finally {
				System.out.println(thread.getName()+"释放了锁");
				lock.unlock();
			}
		}else {
			System.out.println(thread.getName()+"获取锁失败");
		}
	}
}
