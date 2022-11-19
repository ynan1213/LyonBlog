package com.ynan._01.javaAnno;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.util.stream.Stream;

/**
 * @Author yuannan
 * @Date 2021/10/24 10:39
 */
public class TestMain {
	public static void main(String[] args) {

		// 从这里可以看出所有的注解实现了 Annotation 接口
		Class<?>[] interfaces = TestAnnotation.class.getInterfaces();
		// null
		Class<? super TestAnnotation> superclass = TestAnnotation.class.getSuperclass();

		Class<MyClass> clazz = MyClass.class;
		// 获取类上的所有注解
		Annotation[] annotations = clazz.getAnnotations();
		// 获取类上指定的某个注解
		TestAnnotation annotation = clazz.getAnnotation(TestAnnotation.class);
		String[] value = annotation.value();
		String params = annotation.params();

		Class<?> annoClazz = TestAnnotation.class;
		Class<? extends Annotation> annotationType = annotation.annotationType();

		// 获取注解上的注解
		Annotation[] annotations1 = annotationType.getAnnotations();
		System.out.println("---");
	}
}
