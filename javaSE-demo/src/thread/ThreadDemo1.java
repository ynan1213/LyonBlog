package thread;

public class ThreadDemo1 {

	public static void main(String[] args) {
		
		final InsertData i = new InsertData();
		
		new Thread() {
			public void run() {
				i.insert();
			};
		}.start();

		new Thread() {
			public void run() {
				i.insert1();
			}
		}.start();
	}

}

class InsertData{
	public synchronized void insert() {
		System.out.println("执行insert方法");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("执行insert方法完毕");
	}
	
	public synchronized void insert1() {
		System.out.println("执行insert1方法");
		System.out.println("执行insert1方法完毕");
	}
}