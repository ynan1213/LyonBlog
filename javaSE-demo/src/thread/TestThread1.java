package thread;

public class TestThread1 {
	public static void main(String[] args){
		Runner1 r=new Runner1();
		//Thread t=new Thread(r);
		r.start();
		//new Thread(r).start();
		for(int j=1;j<=100;j++){
			System.out.println("j="+j);
		}
	}
}



class Runner1 extends Thread {
	public void run(){
		for(int i=1;i<=100;i++){
			System.out.println("i="+i);
		}
	}
}