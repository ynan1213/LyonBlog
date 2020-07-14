package com.epichust.controller.initializer;

import com.epichust.controller.config.RootConfig;
import com.epichust.controller.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MyServletContainerInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class[]{RootConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings()
    {
        return new String[]{"/*"};
    }
}
