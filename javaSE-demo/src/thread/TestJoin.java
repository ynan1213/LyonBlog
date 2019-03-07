package thread;
//Join �̺߳ϲ�
public class TestJoin {
	public static void main(String[] args){
		Join j=new Join("�߳�");
		j.start();
		try{
			j.join();
		}catch(InterruptedException e){}
		for(int i=1;i<=10;i++){
			System.out.println("i am a thread");
		}
	}
}



class Join extends Thread{
	Join(String s){
		super(s);	//ΪThread����ֵ
	}
	
	public void run(){
		for(int i=1;i<=10;i++){
			System.out.println("i am  "+getName());
			try{
				sleep(1000);
			}catch(InterruptedException e){
				return;
			}
		}
	}
}