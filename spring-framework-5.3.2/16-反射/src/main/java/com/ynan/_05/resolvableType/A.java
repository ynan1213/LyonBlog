package com.ynan._05.resolvableType;

import java.util.regex.Pattern;

/**
 * @Author yuannan
 * @Date 2021/10/25 16:44
 */
public class A {

	public static void main(String[] args) {
		boolean matches = Pattern.matches("^\\d\\d(,\\d\\d)*$", "01,12");
		System.out.println(matches);
	}

}
