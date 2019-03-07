package start20180123.Java;
import java.util.Scanner;
public class D2_Student_Score {
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("������ѧ��������");
		int StuNum=in.nextInt();
		System.out.println("������γ���Ŀ��");
		int CourseNum=in.nextInt();
		String[] CourseName=new String[CourseNum];
		String[] StuName=new String[StuNum];
		int[][] Score=new int[StuNum][CourseNum];
		int[] sum=new int[StuNum];
		String[] Student=new String[StuNum];
		
		for(int i=0;i<CourseNum;i++){
			System.out.println("�������"+(i+1)+"�ſγ����ƣ�");
			CourseName[i]=in.next();
		}
		for(int i=0;i<StuNum;i++){
			System.out.println("�������"+(i+1)+"��ѧ����������");
			StuName[i]=in.next();
			String arr="";
			for(int j=0;j<CourseNum;j++){
				System.out.println("������"+StuName[i]+"��"+CourseName[j]+"�ɼ���");
				Score[i][j]=in.nextInt();
				sum[i]+=Score[i][j];
				arr=arr+Score[i][j]+"\t";
			}
			Student[i]=StuName[i]+"\t"+arr;
		}
		
		
		System.out.print("����\t");
		for(int i=0;i<CourseNum;i++){
			System.out.print(CourseName[i]+"\t");
		}
		System.out.print("�ܷ�\tƽ����\t����\n");
		
		for(int i=0;i<StuNum-1;i++){
			for(int j=0;j<StuNum-1;j++){
				if(sum[j]<sum[j+1]){
					int k=sum[j];		String x=Student[j];
					sum[j]=sum[j+1];	Student[j]=Student[j+1];
					sum[j+1]=k;			Student[j+1]=x;
				}
			}
		}
		
		
		for(int i=0;i<StuNum;i++){
			System.out.print(Student[i]+sum[i]+"\t"+(sum[i]/StuNum)+"\t"+"��"+(i+1)+"��");
			System.out.println();
		}
		
	}
}
