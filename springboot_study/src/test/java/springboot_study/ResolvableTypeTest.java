package springboot_study;

import org.springframework.core.ResolvableType;

public class ResolvableTypeTest
{
	public static void main(String[] args)
	{
		ResolvableType type = ResolvableType.forClass(TypeTest.class);
		
		boolean b = type.hasGenerics();
		
		System.out.println(b);
	}
}
