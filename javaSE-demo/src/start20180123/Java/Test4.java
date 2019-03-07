package start20180123.Java;

import java.util.*;
public class Test4{
	@SuppressWarnings("resource")
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("������ɼ�");
		int year=in.nextInt();
		if(year%4==0&&year%100!=0||year%400==0){
			System.out.println(year+"�Ǹ�����");
		}else {
			System.out.println(year+"�Ǹ�ƽ��");
		}
	}
}