package springboot_lambda;

public class LambdaTest
{
	public static void main(String[] args)
	{
		new LambdaTest().f("str", x -> System.out.println(x));
	}
	
	public void f(String str,Book b)
	{
		b.fun(str);
	}
}
