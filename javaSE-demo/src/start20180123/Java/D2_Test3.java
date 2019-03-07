package start20180123.Java;
import java.util.Scanner;
public class D2_Test3 {
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("������һ������ֵ");
		int Num=in.nextInt();
		for(int i=0;i<=Num;i++){
			System.out.println(i+"+"+(Num-i)+"="+Num);
		}
	}
}
