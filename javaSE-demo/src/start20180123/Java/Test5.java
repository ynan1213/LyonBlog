package start20180123.Java;

import java.util.*;
public class Test5{
	@SuppressWarnings("resource")
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("����Ա��ǣ�");
		String sex=in.next();
		if(sex.equals("��")){
			System.out.println("��������Ƕ���");
			int age=in.nextInt();
			if(age>=18){
				System.out.println("���ѳ���");	
			}else{
				System.out.println("δ����");
			}
		}else{
			System.out.println("ԭ���Ǹ�Ů��");
		}
	}
}