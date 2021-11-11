package com.ynan._05.resolvableType;

import java.lang.reflect.AnnotatedType;

/**
 * @Author yuannan
 * @Date 2021/10/24 22:30
 */
public class TypeMain {

	public static void main(String[] args) {

		AnnotatedType annotatedSuperclass = Son.class.getAnnotatedSuperclass();
		AnnotatedType[] annotatedInterfaces = Son.class.getAnnotatedInterfaces();
		System.out.println("----");

	}
}
