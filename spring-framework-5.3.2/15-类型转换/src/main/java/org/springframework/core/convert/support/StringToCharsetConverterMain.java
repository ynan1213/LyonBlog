package org.springframework.core.convert.support;

import java.nio.charset.Charset;

/**
 * @Author yuannan
 * @Date 2021/10/17 18:33
 */
public class StringToCharsetConverterMain {

	public static void main(String[] args) {
		StringToCharsetConverter converter = new StringToCharsetConverter();
		Charset utf8 = converter.convert("utf8");
	}
}
