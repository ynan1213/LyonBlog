package staticDemo;

public class StaticTest {
	
	public int i = 1;
	
	static{
		System.out.println();
	}
	
	public StaticTest() {
		i++;
		System.out.println(i);
	}
	
	public static void main(String[] args) {
		new StaticTest();
	}
	
}
