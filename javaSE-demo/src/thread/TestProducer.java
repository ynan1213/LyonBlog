package thread;

public class TestProducer {
	public static void main(String[] args){
		
	}
}

class WoTou{
	int id;
	WoTou(int id){
		this.id=id;
	}
	public String toString(){
		return "WoTou :"+id;
	}
}

class SyncStack{
	int index=0;
	WoTou[] arrWT=new WoTou[6];
	
	public synchronized void push(WoTou wt){
		arrWT[index]=wt;
		index ++;
	}
	
	public synchronized WoTou pop(){
		index --;
		return arrWT[index];
	} 
}

class Producer implements Runnable{
	SyncStack ss=null; 
	Producer(SyncStack ss){
		this.ss=ss;
	}
	
	public void run(){
		for(int i=0;i<20;i++){
			WoTou wt=new WoTou(i);
			ss.push(wt);
		}
	}
}

class Consumer implements Runnable{
	SyncStack ss=null; 
	Consumer(SyncStack ss){
		this.ss=ss;
	}
	
	public void run(){
		for(int i=0;i<20;i++){
			WoTou wt=ss.pop();
			System.out.println(wt);
		}
	}
}








