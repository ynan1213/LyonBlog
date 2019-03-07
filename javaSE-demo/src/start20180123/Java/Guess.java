package start20180123.Java;

import java.util.Scanner;

public class Guess{
	@SuppressWarnings("resource")
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("�����壬���ȭ��1������  2��ʯͷ  3������");
		int person=in.nextInt();
		int computer=(int)(Math.random()*3)+1;
		String Marks="����";
		String Marks1="����";
		switch(person){
			case 1:
			Marks="����";
			break;
			case 2:
			Marks="ʯͷ";
			break;
			case 3:
			Marks="��";
			break;
		}
		switch(computer){
			case 1:
			Marks1="����";
			break;
			case 2:
			Marks1="ʯͷ";
			break;
			case 3:
			Marks1="��";
			break;
		}
		if(person==computer){
			System.out.println("�������:"+Marks+"  ���Գ�����:"+Marks1+"  �Ǻǣ�ƽ��");
		}else if(person==1&&computer==2||person==2&&computer==3||person==3&&computer==1){
			System.out.println("�������:"+Marks+"  ���Գ�����:"+Marks1+"  ������");
		}else{
			System.out.println("�������:"+Marks+"  ���Գ�����:"+Marks1+"  ��Ӯ��");
		}
	}
}