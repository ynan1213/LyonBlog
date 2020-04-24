package springboot_study;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.springframework.core.ResolvableType;

@SuppressWarnings({ "serial", "unused", "rawtypes" })
public class TypeTest
{
	public static void main(String[] args) throws NoSuchFieldException, SecurityException
	{
		Field field = Foo.class.getDeclaredField("map");
		
		ParameterizedType type = (ParameterizedType)field.getGenericType();
		System.out.println(type.getClass());//ParameterizedType
		System.out.println(type.getTypeName());//Map
		System.out.println(type.getRawType());//Map
		System.out.println(Arrays.toString(type.getActualTypeArguments()));//String,Integer
		
	}

	static class Foo<E> implements Serializable
	{
		private Map<String, Integer> map;

		private Set set;
		
	}
	
}
