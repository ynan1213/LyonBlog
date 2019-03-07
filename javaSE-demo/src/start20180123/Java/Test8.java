import java.util.*;
public class Test8{
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("请输入学生个数");
		int Number=in.nextInt();
		int num=1;
		double sum=0;
		while(num<=Number){
			System.out.println("请输入"+num+"号学生成绩");
			sum=sum+in.nextDouble();
			num++;
		}
		double Avg=sum/(double)Number;
		System.out.println("全班平均分是"+Avg);
	}
}