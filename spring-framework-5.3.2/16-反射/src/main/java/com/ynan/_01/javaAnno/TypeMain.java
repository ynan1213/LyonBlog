package com.ynan._01.javaAnno;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author yuannan
 * @date 2022/11/5 11:17
 */
public class TypeMain {

	public static void main(String[] args) {

		Type[] genericInterfaces = Son.class.getGenericInterfaces();
		Type genericSuperclass = Son.class.getGenericSuperclass();

		ParameterizedType type = (ParameterizedType)genericSuperclass;

		Type[] actualTypeArguments = type.getActualTypeArguments();
		Type ownerType = type.getOwnerType();
		Type rawType = type.getRawType();
		String typeName = type.getTypeName();
		System.out.println("----");
	}

}
