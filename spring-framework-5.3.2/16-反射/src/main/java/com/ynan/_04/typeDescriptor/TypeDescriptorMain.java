package com.ynan._04.typeDescriptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;

/**
 * @Author yuannan
 * @Date 2021/10/17 21:05
 */
public class TypeDescriptorMain {

	public static void main(String[] args) {
		TypeDescriptor typeDescriptor = TypeDescriptor.valueOf(User.class);
		System.out.println(typeDescriptor.getType());
		System.out.println(typeDescriptor.getElementTypeDescriptor());
		System.out.println(typeDescriptor.getSource());

		//		TypeDescriptor typeDescriptor = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(String.class));
//		System.out.println(typeDescriptor.getType().getName());
//		System.out.println(typeDescriptor.getElementTypeDescriptor().getType().getName());

	}
}
