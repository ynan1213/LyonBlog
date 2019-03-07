package start20180123.Java;
//import java.util.Scanner;
import java.util.Random;
public class D2_Test4 {
	public static void main(String[] args){
		//Scanner in=new Scanner(System.in);
		int sum=0;
		for(int i=1;i<=100;i++){
			Random xx=new Random();
			int Num=xx.nextInt(100);
			sum=sum+Num;
		}
		System.out.print(sum/100);
	}
}
