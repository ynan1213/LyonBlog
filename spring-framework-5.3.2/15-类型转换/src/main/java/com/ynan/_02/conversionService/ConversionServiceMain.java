package com.ynan._02.conversionService;

import java.util.Collections;
import java.util.stream.Stream;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

/**
 * @Author yuannan
 * @Date 2021/10/29 20:25
 */
public class ConversionServiceMain {

	public static void main(String[] args) {

		ConversionService conversionService = new DefaultConversionService();
		// Stream result = conversionService.convert(Collections.singleton(1), Stream.class);

	}
}
