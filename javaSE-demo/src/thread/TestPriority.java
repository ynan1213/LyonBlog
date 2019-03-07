package thread;

public class TestPriority {
	public static void main(String[] args){
		new Thread(new P1()).start();
		Thread pr2=new Thread(new P2());
		pr2.setPriority(Thread.MAX_PRIORITY);
		pr2.start();
		//new Thread(new P2()).setPriority(5).start();
	}
}

class P1 implements Runnable{
	public void run(){
		for(int i=1;i<=1000;i++){
			System.out.println("i========= "+i);
		}
	}
}

class P2 implements Runnable{
	public void run(){
		for(int j=1;j<=1000;j++){
			System.out.println("j= "+j);
		}
	}
}