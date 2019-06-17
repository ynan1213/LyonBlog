package com.sqsoft.test;

import java.lang.reflect.Method;

public class D  {

	String id(String t) {
		return "aaa";
	}
	
	public static void main(String[] args) {
		Method[] methods = D.class.getDeclaredMethods();
		for (Method method : methods) {
			System.out.println(method);
		}
	}
	
}
