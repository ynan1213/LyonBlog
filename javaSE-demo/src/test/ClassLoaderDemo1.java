package test;

public class ClassLoaderDemo1 {

	public static void main(String[] args) {
		
		ClassLoader loader = ClassLoaderDemo1.class.getClassLoader();
		System.out.println(loader.toString());
		System.out.println(loader.getParent().toString());
		System.out.println(loader.getParent().getParent().toString());
		
//		ClassLoader loader2 = String.class.getClassLoader();
//		System.out.println(loader2.toString());
		
		
		
	}
}
