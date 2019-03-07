package string;

public class StringDemo1 {
	
	public static void main(String[] args) throws Exception {
		
		Test test = new Test();
		
		Thread t1 = new Thread(test,"线程1");
		Thread t2 = new Thread(test,"线程2");
		
		t1.start();
		t1.join();
		t2.start();
		
	}
}


class Test implements Runnable{
	
	@Override
	public void run() {
		for(int i=0; i<100; i++) {
			System.out.println("线程：" + Thread.currentThread().getName()+":"+Thread.currentThread().getPriority());
		}
	}
	
}