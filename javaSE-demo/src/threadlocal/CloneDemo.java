package threadlocal;

public class CloneDemo {
	
	public static void main(String[] args) throws CloneNotSupportedException {
		Dog d = new Dog();
		Dog d1 = (Dog)d.clone();
		System.out.println(d == d1);
	}
	
	
}
