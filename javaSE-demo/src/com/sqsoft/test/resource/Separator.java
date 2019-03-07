package com.sqsoft.test.resource;

import java.net.URL;

public class Separator {

	public static void main(String[] args) {
		
		Class<Separator> clazz = Separator.class;
		
		ClassLoader loader = clazz.getClassLoader();
		
		URL url = loader.getResource("com/sqsoft/test/resource/C1.class");
		loader.getResourceAsStream("a");
		
		System.out.println(url.toString());
	}

}
