package threadlocal;

public class Demo {
	
	ThreadLocal<Long> longLocal = new ThreadLocal<Long>();
	ThreadLocal<String> stringLocal = new ThreadLocal<String>();
	
	public void set() {
		longLocal.set(Thread.currentThread().getId());
		stringLocal.set(Thread.currentThread().getName());
	}
	
	public Long getLong() {
		return longLocal.get();
	}
	
	public String getString() {
		return stringLocal.get();
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		Demo demo = new Demo();
		demo.set();
		
		System.out.println(demo.getLong() +"=="+ demo.getString());
		
		Thread thread = new Thread() {
			public void run() {
				demo.set();	
				System.out.println(demo.getLong() +"=="+ demo.getString());
			};
		};
		
		thread.start();
		thread.join();
		
		System.out.println(demo.getLong() +"=="+ demo.getString());
	}
}
