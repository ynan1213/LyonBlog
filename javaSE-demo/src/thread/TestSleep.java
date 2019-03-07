package thread;
import java.util.*;
public class TestSleep {
	public static void main(String[] args){
		MyThread t=new MyThread();
		t.start();
		try{
			t.sleep(5000);
		}catch(InterruptedException e){}
		t.interrupt();
	}
}




class MyThread extends Thread{
	public void run(){
		while(true){
			System.out.println("==="+new Date()+"===");
			try{
				sleep(1000);
			}catch(InterruptedException e){
				return;		//�Ӳ���return�����һ�����˴�Ҫע��(return�ؼ���δŪ��)		
			}
		}
		
	}
}