package start20180123.Java;

import java.util.Scanner;
public class Student_Score {
	@SuppressWarnings("resource")
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("������ѧ������");
		int StuNum=in.nextInt();
		System.out.println("������γ���Ŀ");
		int CourseNum=in.nextInt();
		String[] CourseName=new String[CourseNum];
		String[] StuName=new String[StuNum];
		
		for(int i=1;i<=CourseNum;i++){
			System.out.println("�������"+i+"�ſγ�����");
			CourseName[i]=in.next();
		}
		for(int i=1;i<=StuNum;i++){
			System.out.println("�������һ��ѧ��������");
			StuName[i]=in.next();
			for(int j=1;j<=CourseNum;j++){
				System.out.println("������"+StuName[i]+"��"+CourseName[j]+"�ɼ�");
				
			}
		}
	}
}