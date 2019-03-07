package thread;


public class TestSync1 implements Runnable{
	private int b=100;
	
	public synchronized void m1() throws Exception{
		b=1000;
		Thread.sleep(5000);
		System.out.println("1b= "+b);
	} 
	
	public synchronized void m2()throws Exception{
		Thread.sleep(2500);
		b=2000;
		System.out.println("2b= "+b);
	}
	
	public void run(){
		try{
			m1();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception{
		TestSync1 s=new TestSync1();
		Thread t=new Thread(s);
		t.start();
		
		s.m2();
		System.out.println("����b= "+s.b);
	}
}
