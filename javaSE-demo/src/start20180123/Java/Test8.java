package start20180123.Java;

import java.util.*;
public class Test8{
	@SuppressWarnings("resource")
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("������ѧ������");
		int Number=in.nextInt();
		int num=1;
		double sum=0;
		while(num<=Number){
			System.out.println("������"+num+"��ѧ���ɼ�");
			sum=sum+in.nextDouble();
			num++;
		}
		double Avg=sum/(double)Number;
		System.out.println("ȫ��ƽ������"+Avg);
	}
}