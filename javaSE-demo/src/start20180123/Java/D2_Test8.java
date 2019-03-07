package start20180123.Java;
//���������ֵ ����������
import java.util.Scanner;
import java.util.Arrays;
public class D2_Test8 {
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
		Arrays.sort(arr);//��������
		System.out.println("������Ҫ�����������");
		int num=in.nextInt();
		arr[arr.length-1]=num;
		for(int i=arr.length-1;i>=0;i--){
			if(arr[i]<arr[i-1]){
				int t=arr[i];
				arr[i]=arr[i-1];
				arr[i-1]=t;
			}else{
				break;
			}
		}
		System.out.println("���º������Ϊ");
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+"\t");
		}
	}
}
