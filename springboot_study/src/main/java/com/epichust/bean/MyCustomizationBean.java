package com.epichust.bean;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyCustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
{
    @Override
    public void customize(ConfigurableServletWebServerFactory factory)
    {

    }
}
