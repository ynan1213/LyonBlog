package test;

class Fu {
	
	public int num = 100;

	public void show() {
		System.out.println("show Fu");
	}

	public static void function() {
		System.out.println("function Fu");
	}
}

class Zi extends Fu {
	
	public int num = 1000;
	public int num2 = 200;

	public void show() {
		System.out.println("show Zi");
	}

	public void method() {
		System.out.println("method zi");
	}

	public static void function() {
		System.out.println("function Zi");
	}
}

public class DuoTaiDemo {
	
	public static void main(String[] args) {		

		Fu f = new Zi();		
		System.out.println(f.num);
		
		//找不到符号		
//		System.out.println(f.num2);
		f.show();		
		//找不到符号		
		//f.method();		
//		f.function();	
	}
}
