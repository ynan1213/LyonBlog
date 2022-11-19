package com.ynan._02.annotatedElement;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @Author yuannan
 * @Date 2021/10/24 11:07
 */
public class TestMain {

	public static void main(String[] args) {

		Annotation[] annotations = Son.class.getAnnotations();
		System.out.println("getAnnotations: " + Arrays.asList(annotations));

		Annotation[] declaredAnnotations = Son.class.getDeclaredAnnotations();
		System.out.println("getDeclaredAnnotations: " + Arrays.asList(declaredAnnotations));

		// ------------
		YeAnnotation annotation = Son.class.getAnnotation(YeAnnotation.class);
		System.out.println("getAnnotation: " + annotation);
		YeAnnotation[] annotationsByType = Son.class.getAnnotationsByType(YeAnnotation.class);
		System.out.println(annotationsByType.length == 0);

	}

}
