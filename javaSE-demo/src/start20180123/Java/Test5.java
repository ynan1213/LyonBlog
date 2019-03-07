import java.util.*;
public class Test5{
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("你的性别是？");
		String sex=in.next();
		if(sex.equals("男")){
			System.out.println("你的年龄是多少");
			int age=in.nextInt();
			if(age>=18){
				System.out.println("你已成年");	
			}else{
				System.out.println("未成年");
			}
		}else{
			System.out.println("原来是个女的");
		}
	}
}