package com.ynan._08.注解合成;

import java.util.HashMap;
import java.util.Map;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.SynthesizedAnnotation;

/**
 * @Author yuannan
 * @Date 2021/11/17 11:10
 */
public class SynthesizeAnnotationMain {

	public static void main(String[] args) {
		MyAnnotation myAnnotation = AnnoDemo.class.getAnnotation(MyAnnotation.class);
		System.out.println("原生方式: " + myAnnotation.value());
		System.out.println("原生方式: " + myAnnotation.attribute());
		System.out.println("原生方式: " + myAnnotation.condition());

		// 合成注解
		MyAnnotation synthesize = AnnotationUtils.synthesizeAnnotation(myAnnotation, null);
		System.out.println("合成方式: " + synthesize.value());
		System.out.println("合成方式: " + synthesize.attribute());
		System.out.println("合成方式: " + synthesize.condition());

		System.out.println("合成后的注解实现了MyAnnotation？ " + (synthesize instanceof MyAnnotation));
		System.out.println("合成后的注解实现了SynthesizedAnnotation？ " + (synthesize instanceof SynthesizedAnnotation));

		// 获取原先的属性值
		Map<String, Object> attributes = new HashMap<>(AnnotationUtils.getAnnotationAttributes(myAnnotation));
		// 加个前缀
		attributes.put("condition", "==" + attributes.get("condition"));
		MyAnnotation synthesizeAnnotation = AnnotationUtils.synthesizeAnnotation(attributes, MyAnnotation.class, null);
		System.out.println("合成属性方式: " + synthesizeAnnotation.value());
		System.out.println("合成属性方式: " + synthesizeAnnotation.attribute());
		System.out.println("合成属性方式: " + synthesizeAnnotation.condition());
	}
}
