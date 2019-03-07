package com.sqsoft.test.resource;

public class TT {


	public static void main(String[] args) {
		
		String baseName = "com.sqsoft.test.resource.Separator";
		
		String name = "C1.class";
		
		int index = baseName.lastIndexOf('.');
        if (index != -1) {
            name = baseName.substring(0, index).replace('.', '/')
                +"/"+name;
        }
        
        System.out.println(name);

	}

}
