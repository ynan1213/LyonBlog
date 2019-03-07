package Array;

//Óö3ÍËÒ»

public class TestArray2 {
	public static void main(String[] args){
		boolean[] arr=new boolean[500];
		for(int i=0;i<500;i++){
			arr[i]=true;
		}
		int number=500;
		int index=0;
		int count=0;
		while(number>1){
			if(arr[index]==true){
				count++;
			}
			if(count==3){
				arr[index]=false;
				count=0;
				number--;
			}
			index++;
			if(index==500){
				index=0;
			}
		}
		for(int i=0;i<500;i++){
			if(arr[i]==true){
				System.out.println(i+1);
			}
		}
	}
}


