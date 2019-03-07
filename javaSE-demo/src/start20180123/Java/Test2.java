import java.util.Scanner;
public class Test2{
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("请输入你的成绩：");
		int score=in.nextInt();
		if(score>90==false){
			System.out.println("奖励一根棒棒糖");
		}
	}
}