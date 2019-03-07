package start20180123.Java;
import java.util.Scanner;
public class D2_Test7 {
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("������ѧ���ĸ���");
		int num=in.nextInt();
		double[] Number=new double[num];
		double sum=0;
		for(int i=0;i<Number.length;i++){
			System.out.println("������"+(i+1)+"ѧ���ĳɼ�");
			double score=in.nextDouble();
			Number[i]=score;
			sum+=score;
		}
		System.out.println(num+"��ѧ����ƽ���ɼ�Ϊ"+sum/num);
	}
}
