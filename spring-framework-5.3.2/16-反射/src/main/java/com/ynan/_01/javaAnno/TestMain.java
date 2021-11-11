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
		Class<MyClass> clazz = MyClass.class;

		TestAnnotation annotation = clazz.getAnnotation(TestAnnotation.class);
		Class<? extends Annotation> annotationType = annotation.annotationType();

		Annotation[] annotations = annotationType.getAnnotations();
		Stream.of(annotations).forEach(an -> System.out.println(an.toString()));

		clazz.getGenericSuperclass();
	}
}
