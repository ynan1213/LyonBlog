package com.ynan._09.AnnotationUtils;

import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author yuannan
 */
public class MainTest01 {

	public static void main(String[] args) {
		boolean candidateClass = AnnotationUtils.isCandidateClass(Xxx.class, Yyy.class);
		System.out.println(candidateClass);
	}
}