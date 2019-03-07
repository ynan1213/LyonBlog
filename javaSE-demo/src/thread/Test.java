package thread;

/**
 * 阻塞状态下可以interrupt（）中断
 * 运行状态下无法中断
 */
public class Test {
	
	public static void main(String[] args) {
		
		MyThread thread = new Test().new MyThread();
		thread.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
		}
		thread.interrupt();
	}
	
	class MyThread extends Thread{
		@Override
		public void run() {
			System.out.println("进入run方法");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				System.out.println("run方法得到中断异常");
			}
			System.out.println("退出run方法");
		}
	}
}
