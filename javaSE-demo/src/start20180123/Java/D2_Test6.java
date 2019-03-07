package start20180123.Java;
//����
import java.util.Scanner;
public class D2_Test6 {
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("���������");
		int year=in.nextInt();
		int sum=0;
		for(int i=1900;i<year;i++){
			if(i%4==0&&i%100!=0||i%400==0){
				sum+=366;
			}else{
				sum+=365;
			}
		}
		//System.out.println(sum);
		System.out.println("�������·�");
		int month=in.nextInt();
		for(int i=1;i<month;i++){
			if(i==2){
				if(year%4==0&&year%100!=0||year%400==0){
					sum+=29;
				}else{
					sum+=28;
				}
			}else if(i==4||i==6||i==9||i==11){
				sum+=30;
			}else{
				sum+=31;
			}
			
		}
		sum+=1;
		int day=sum%7;
		//System.out.println(day);
		System.out.println("��\tһ\t��\t��\t��\t��\t��");
		if(month==2){
			if(year%4==0&&year%100!=0||year%400==0){
				for(int k=1;k<=day;k++){
					System.out.print("\t");
				}
				for(int i=1;i<=29;i++){
					if(sum%7==6){
						System.out.print(i+"\n");
					}else{
						System.out.print(i+"\t");
					}
					sum+=1;
				}
			}else{
				for(int k=1;k<=day;k++){
					System.out.print("\t");
				}
				for(int i=1;i<=28;i++){
					if(sum%7==6){
						System.out.print(i+"\n");
					}else{
						System.out.print(i+"\t");
					}
					sum+=1;
				}	
			}
		}else if(month==4||month==6||month==9||month==11){
			for(int k=1;k<=day;k++){
				System.out.print("\t");
			}
			for(int i=1;i<=30;i++){
				if(sum%7==6){
					System.out.print(i+"\n");
				}else{
					System.out.print(i+"\t");
				}
				sum+=1;
			}
		}else {
			for(int k=1;k<=day;k++){
				System.out.print("\t");
			}
			for(int i=1;i<=31;i++){
				if(sum%7==6){
					System.out.print(i+"\n");
				}else{
					System.out.print(i+"\t");
				}
				sum+=1;
			}
		}
	}
}	
