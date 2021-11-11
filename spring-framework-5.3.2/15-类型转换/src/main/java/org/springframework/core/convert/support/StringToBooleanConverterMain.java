package org.springframework.core.convert.support;

import org.springframework.core.convert.support.StringToBooleanConverter;

/**
 * @Author yuannan
 * @Date 2021/10/17 18:27
 */
public class StringToBooleanConverterMain {

	public static void main(String[] args) {
		StringToBooleanConverter converter = new StringToBooleanConverter();
		Boolean convert = converter.convert("off");
		System.out.println(convert);
	}
}
