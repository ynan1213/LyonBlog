package innerclass;

public class InnerClass {

	static {
		System.out.println("外部类被加载");
	}
	
	
	static class Inner1{
		static {
			System.out.println("Inner1 被加载");
		}
	}
	
}
