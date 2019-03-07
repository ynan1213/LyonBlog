import java.util.Scanner;
public class Test1{
	public static void main(String[] args){
		Scanner Nan=new Scanner(System.in);
		System.out.println("请输入你的名字");
		String name=Nan.next();
		System.out.println("请输入你的年龄");
		int age=Nan.nextInt();
		System.out.println("姓名:"+name);
		System.out.println("年龄:"+age);
	}
}