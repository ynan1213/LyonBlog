package start20180123.Java;
//����ѡ������
import java.util.Scanner;
//import java.util.Arrays;
public class D2_Test9 {
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("����������ĳ���");
		int k=in.nextInt();
		int[] arr=new int[k];
		//System.out.println("�������������");
		for(int i=0;i<k;i++){
			System.out.println("�������"+(i+1)+"����");
			arr[i]=in.nextInt();
		}
		for(int i=0;i<arr.length-1;i++){
			for(int j=i+1;j<arr.length;j++){
				if(arr[i]>arr[j]){
					int t=arr[i];
					arr[i]=arr[j];
					arr[j]=t;
				}
			}
		}
		System.out.println("���º������Ϊ");
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+"\t");
		}
	}
}
