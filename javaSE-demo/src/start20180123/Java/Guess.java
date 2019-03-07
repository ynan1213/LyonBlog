import java.util.*;
public class Guess{
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("柯子渝，请出拳（1、剪刀  2、石头  3、布）");
		int person=in.nextInt();
		int computer=(int)(Math.random()*3)+1;
		String Marks="保密";
		String Marks1="保密";
		switch(person){
			case 1:
			Marks="剪刀";
			break;
			case 2:
			Marks="石头";
			break;
			case 3:
			Marks="布";
			break;
		}
		switch(computer){
			case 1:
			Marks1="剪刀";
			break;
			case 2:
			Marks1="石头";
			break;
			case 3:
			Marks1="布";
			break;
		}
		if(person==computer){
			System.out.println("你出的是:"+Marks+"  电脑出的是:"+Marks1+"  呵呵，平局");
		}else if(person==1&&computer==2||person==2&&computer==3||person==3&&computer==1){
			System.out.println("你出的是:"+Marks+"  电脑出的是:"+Marks1+"  你输了");
		}else{
			System.out.println("你出的是:"+Marks+"  电脑出的是:"+Marks1+"  你赢了");
		}
	}
}