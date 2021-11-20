package com.ynan._07.reflectionUtils;

import java.lang.reflect.Method;
import org.springframework.util.ReflectionUtils;

/**
 * @Author yuannan
 * @Date 2021/11/17 10:04
 */
public class ReflectionUtilsMain {

	public static void main(String[] args) {
		Method[] allDeclaredMethods = ReflectionUtils.getAllDeclaredMethods(XiaoMing.class);
		for (Method declaredMethod : allDeclaredMethods) {
			System.out.println(declaredMethod.getDeclaringClass() + ":" + declaredMethod.getName());
		}
		System.out.println("===========================================================================");
		Method[] uniqueDeclaredMethods = ReflectionUtils.getUniqueDeclaredMethods(XiaoMing.class);
		for (Method declaredMethod : uniqueDeclaredMethods) {
			System.out.println(declaredMethod.getDeclaringClass() + ":" + declaredMethod.getName());
		}
	}
}
