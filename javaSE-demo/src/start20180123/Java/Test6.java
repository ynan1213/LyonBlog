import java.util.*;
public class Test6{
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("你的名次");
		int mingci=in.nextInt();
		switch(mingci){
			case 1:
			System.out.println("武林盟主");
			break;

			case 2:
			System.out.println("武当掌门");
			break;
			
			default:
			System.out.println("逐出师门");
		}
	}
}