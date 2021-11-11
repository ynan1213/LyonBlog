package com.ynan._03.annotatedElementUtils;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;

/**
 * @Author yuannan
 * @Date 2021/10/24 18:14
 */
@TestRoot
public class TestMain {

	public static void main(String[] args) {
		AnnotationAttributes root = AnnotatedElementUtils.findMergedAnnotationAttributes(TestMain.class, TestRoot.class, false, true);
		AnnotationAttributes a = AnnotatedElementUtils.findMergedAnnotationAttributes(TestMain.class, TestA.class, false, true);
		AnnotationAttributes b = AnnotatedElementUtils.findMergedAnnotationAttributes(TestMain.class, TestB.class, false, true);
		AnnotationAttributes c = AnnotatedElementUtils.findMergedAnnotationAttributes(TestMain.class, TestC.class, false, true);
		System.out.println(root);
	}
}
