package com.ynan._05.resolvableType;

import java.util.stream.Stream;
import org.springframework.core.ResolvableType;

/**
 * @Author yuannan
 * @Date 2021/10/24 22:44
 */
public class ResolvableTypeMain {

	public static void main(String[] args) {
		ResolvableType sonType = ResolvableType.forClass(Son.class);
		ResolvableType[] generics = sonType.getGenerics();
		Stream.of(generics).forEach(type -> {
			System.out.println(type.getGeneric(0).resolve().toGenericString());
		});
		System.out.println("---------------------------------------------------");
		ResolvableType superType = sonType.getSuperType();
		ResolvableType[] generics1 = superType.getGenerics();
		System.out.println(generics1[0].resolve().toGenericString());
		System.out.println(generics1[1].resolve().toGenericString());

		System.out.println("---------------------------------------------------");
		ResolvableType[] interfaces = sonType.getInterfaces();
		ResolvableType[] generics2 = interfaces[0].getGenerics();
		System.out.println(generics2[0].resolve().toGenericString());
		System.out.println(generics2[1].resolve().toGenericString());
	}
}
