package com.sqsoft.service;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class MyArgumentsResolver extends AbstractNamedValueMethodArgumentResolver
{

    @Override
    protected NamedValueInfo createNamedValueInfo(MethodParameter parameter)
    {
        return null;
    }

    @Override
    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception
    {
        return null;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter)
    {
        return false;
    }
}
