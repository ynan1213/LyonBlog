package com.ynan._05.resolvableType;

import java.util.stream.Stream;
import org.springframework.core.ResolvableType;

/**
 * @Author yuannan
 * @Date 2021/10/23 21:19
 */
public class ResolvableTypeMain1 {

	public static void main(String[] args) {
		ResolvableType sonType = ResolvableType.forClass(Son.class);
		ResolvableType[] generics = sonType.getGenerics();
		Stream.of(generics).forEach(type -> {
			System.out.println(type.getGeneric(0).resolve().toGenericString());
		});

		System.out.println("---------------------------------------------------");
		ResolvableType superType = sonType.getSuperType();
		ResolvableType[] generics1 = superType.getGenerics();
		Stream.of(generics1).forEach(type -> {
			System.out.println(type.getGeneric(0).resolve().toGenericString());
		});

		System.out.println("---------------------------------------------------");
		ResolvableType[] interfaces = sonType.getInterfaces();
		ResolvableType[] generics2 = interfaces[0].getGenerics();
		Stream.of(generics2).forEach(type -> {
			System.out.println(type.getGeneric(0).resolve().toGenericString());
		});
	}
}
