package com.epichust.example4.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.ParseException;

@Aspect
@Component
public class AnnotationHandler
{
    @Pointcut("@annotation(com.epichust.example4.config.InterfaceLog)")
    public void annotationAspect()
    {
    }

    @Around("annotationAspect() && @annotation(log)")
    public void around(ProceedingJoinPoint joinPoint, InterfaceLog log) throws Throwable
    {
        String code = log.code();
        String name = log.name();
        joinPoint.proceed();
    }
}
