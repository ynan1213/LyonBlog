import java.util.*;
public class Test4{
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("请输入成绩");
		int year=in.nextInt();
		if(year%4==0&&year%100!=0||year%400==0){
			System.out.println(year+"是个瑞年");
		}else {
			System.out.println(year+"是个平年");
		}
	}
}