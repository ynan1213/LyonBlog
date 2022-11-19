package com.ynan._01.javaAnno;

/**
 * @Author yuannan
 * @Date 2021/10/24 10:35
 */
@TestAnnotation(value = "class ....", params = "class param")
public class MyClass {

	@TestAnnotation("name ....")
	private String name;

	@TestAnnotation("age ....")
	private Integer age;
}
