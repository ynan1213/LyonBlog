package thread;
//Yield���ó�cpu���������߳�ִ��
public class TestYield {
	public static void main(String[] args){
		Test_Yield y1=new Test_Yield("�߳�1");
		Test_Yield y2=new Test_Yield("�߳�2");
		y1.start();
		y2.start();
	}

}

class Test_Yield extends Thread{
	Test_Yield(String s){
		super(s);
	}
	public void run(){
		for(int i=1;i<=100;i++){
			System.out.println(getName()+" i= "+i);
			if(i%10==0){
				yield();
			}
		}
	}
}