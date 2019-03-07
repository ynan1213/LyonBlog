import java.util.Scanner;
public class Test3{
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("请输入java成绩:");
		int Java=in.nextInt();
		System.out.println("请输入SQL成绩:");
		int SQL=in.nextInt();
		if(Java>90&&SQL>90){
			System.out.println("奖励一篇奖状");
		}
	}
}