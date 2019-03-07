import java.util.Scanner;
public class Student_Score {
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		System.out.println("请输入学生个数");
		int StuNum=in.nextInt();
		System.out.println("请输入课程数目");
		int CourseNum=in.nextInt();
		String[] CourseName=new String[CourseNum];
		String[] StuName=new String[StuNum];
		
		for(int i=1;i<=CourseNum;i++){
			System.out.println("请输入第"+i+"门课程名称");
			CourseName[i]=in.next();
		}
		for(int i=1;i<=StuNum;i++){
			System.out.println("请输入第一个学生的姓名");
			StuName[i]=in.next();
			for(int j=1;j<=CourseNum;j++){
				System.out.println("请输入"+StuName[i]+"的"+CourseName[j]+"成绩");
				
			}
		}
	}
}