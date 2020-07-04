package com.epichust.service.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class[]{RootConfig.class};
    }

    protected Class<?>[] getServletConfigClasses()
    {
        return new Class[0];
    }

    protected String[] getServletMappings()
    {
        return new String[0];
    }
}
