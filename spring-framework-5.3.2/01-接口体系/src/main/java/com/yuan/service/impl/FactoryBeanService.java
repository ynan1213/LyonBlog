package com.epichust.service.impl;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class FactoryBeanService implements FactoryBean<UserService2>
{
    @Override
    public UserService2 getObject() throws Exception
    {
        return new UserService2();
    }

    @Override
    public Class<?> getObjectType()
    {
        return UserService2.class;
    }

    @Override
    public boolean isSingleton()
    {
        return true;
    }
}
