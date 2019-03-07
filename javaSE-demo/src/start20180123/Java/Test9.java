import java.util.*;
public class Test9{
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		Random MyRandom=new Random();
		int computer=MyRandom.nextInt(5);
		int num;
		do{
			System.out.println("请猜一下电脑随机生成的数（1-5）");
			num=in.nextInt();
			if(num<computer){
				System.out.println("小了");
			}else if(num>computer){
				System.out.println("大了");
			}
		}while(num!=computer);{
			System.out.println("恭喜你猜对了");
		}
	}
}