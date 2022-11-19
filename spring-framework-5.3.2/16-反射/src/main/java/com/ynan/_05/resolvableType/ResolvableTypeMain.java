package com.ynan._05.resolvableType;

import java.lang.reflect.Type;
import java.util.stream.Stream;
import org.springframework.core.ResolvableType;

/**
 * @Author yuannan
 * @Date 2021/10/24 22:44
 */
public class ResolvableTypeMain {

	public static void main(String[] args) {
		ResolvableType sonType = ResolvableType.forClass(Son.class);
		// 获取当前类上的泛型，注意son上的并不是泛型，而是泛型变量
		ResolvableType[] generics = sonType.getGenerics();
//		Stream.of(generics).forEach(type -> {
//			System.out.println(type.getGeneric(0).resolve().toGenericString());
//		});
		System.out.println("---------------------------------------------------");
		// 获取父类
		ResolvableType superType = sonType.getSuperType();
		ResolvableType[] fatherGenerics = superType.getGenerics();
		// 获取第一个泛型
		Class<?> f1 = fatherGenerics[0].resolve();
		Class<?> f2 = fatherGenerics[1].resolve();

		Class<?> resolve = superType.getGeneric(1).getGeneric(0).resolve();

		System.out.println("---------------------------------------------------");
		// 获取接口
		ResolvableType[] interfaces = sonType.getInterfaces();
		ResolvableType[] generics2 = interfaces[0].getGenerics();
		System.out.println(generics2[0].resolve().toGenericString());
		System.out.println(generics2[1].resolve().toGenericString());
	}
}
