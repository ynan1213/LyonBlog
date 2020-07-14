package com.epichust.service.impl;


import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class BeanService implements ImportSelector
{
    public void print()
    {
        System.out.println("@Bean 测试");
    }

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata)
    {
        return new String[0];
    }

}
