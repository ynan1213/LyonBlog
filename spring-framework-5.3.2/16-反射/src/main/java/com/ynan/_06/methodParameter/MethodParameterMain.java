package com.ynan._06.methodParameter;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Optional;
import org.springframework.core.MethodParameter;

/**
 * @Author yuannan
 * @Date 2021/10/31 09:50
 */
public class MethodParameterMain {

	public static void main(String[] args) throws NoSuchMethodException {

		Class<HelloController> clazz = HelloController.class;
		Method helloMethod = clazz.getDeclaredMethod("hello", Optional.class, String.class);

		MethodParameter methodParameter = new MethodParameter(helloMethod, 0);

		Type nestedGenericParameterType = methodParameter.getNestedGenericParameterType();
		System.out.println(nestedGenericParameterType.getTypeName());


		MethodParameter methodParameter1 = methodParameter.nestedIfOptional();
		Class<?> nestedParameterType = methodParameter1.getNestedParameterType();
		System.out.println(nestedParameterType.getName());


	}
}
