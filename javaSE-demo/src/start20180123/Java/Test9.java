package start20180123.Java;

import java.util.*;
public class Test9{
	@SuppressWarnings("resource")
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		Random MyRandom=new Random();
		int computer=MyRandom.nextInt(5);
		int num;
		do{
			System.out.println("���һ�µ���������ɵ�����1-5��");
			num=in.nextInt();
			if(num<computer){
				System.out.println("С��");
			}else if(num>computer){
				System.out.println("����");
			}
		}while(num!=computer);{
			System.out.println("��ϲ��¶���");
		}
	}
}