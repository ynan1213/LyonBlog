package com.epichust.service.impl;


import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class BeanService implements ImportSelector
{
    public void print()
    {
        System.out.println("@Import 测试");
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata)
    {
		System.out.println("@Bean selectImports ======================");
        return new String[]{String.class.getName()};
    }

	public static void main(String[] args)
	{
		System.out.println(BeanService.class.getName());
	}

}
