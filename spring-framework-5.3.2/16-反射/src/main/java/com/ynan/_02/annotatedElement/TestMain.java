package com.ynan._02.annotatedElement;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @Author yuannan
 * @Date 2021/10/24 11:07
 */
public class TestMain {

	public static void main(String[] args) {

		Class<Son> clazz = Son.class;

		Annotation[] annotations = clazz.getAnnotations();
		System.out.println("getAnnotations: " + Arrays.asList(annotations));

		Annotation[] declaredAnnotations = clazz.getDeclaredAnnotations();
		System.out.println("getDeclaredAnnotations: " + Arrays.asList(declaredAnnotations));

		// ------------
		YeAnnotation annotation = clazz.getAnnotation(YeAnnotation.class);
		System.out.println("getAnnotation: " + annotation);
		YeAnnotation[] annotationsByType = clazz.getAnnotationsByType(YeAnnotation.class);
		System.out.println(annotationsByType.length == 0);

	}

}
