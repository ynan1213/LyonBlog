package test;

class A extends B {

	int i = 111;

	public void print() {
		System.out.println("function A=============");
	}
}

class B extends C {

	int i = 222;

	public void print() {
		System.out.println("function B=============");
	}
}

class C {

	int i = 333;

	public void print() {
		System.out.println("function C=============");
	}
}

public class T{
	public static void main(String[] args) {
		C c1 = new C();
		System.out.println(c1.i);
		c1.print();
		
		C c2 = new B();
		System.out.println(c2.i);
		c2.print();
		
		C c3 = new A();
		System.out.println(c3.i);
		c3.print();
	}
}
