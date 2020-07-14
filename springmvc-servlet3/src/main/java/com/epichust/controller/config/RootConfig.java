package com.epichust.controller.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Configurable
@ComponentScan(value = "com.epichust.service", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = {Controller.class, ControllerAdvice.class, RestControllerAdvice.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {WebConfig.class})
})
public class RootConfig
{
}
