package springboot_study;

import org.springframework.core.ResolvableType;

public class TypeTest2<E>
{
	public static void main(String[] args) throws NoSuchFieldException, SecurityException
	{
		ResolvableType type = ResolvableType.forClass(Son.class);
		
		ResolvableType type2 = type.as(Father.class);
		ResolvableType[] types = type2.getGenerics();
		for (ResolvableType r : types)
		{
			System.out.println(r.getType());
		}
	}
}
