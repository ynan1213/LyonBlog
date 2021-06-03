package com.epichust.example4.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.sound.midi.Soundbank;

@Aspect
@Component
public class LogHandler
{
    @Pointcut(value = "execution(* com.epichust.example4.service.*.*(..))")
    public void xxxx()
    {
    }

    //@Before("xxxx()")
    public void logBefore(JoinPoint point)
    {
        Object[] args = point.getArgs();
        System.out.println("============== before ==============");
    }

    //@Around("xxxx()")
    public void arount(ProceedingJoinPoint point) throws Throwable
    {
        long l1 = System.currentTimeMillis();
        System.out.println("============== around before ==============");
        point.proceed();
        System.out.println("============== around after ==============");
        long l2 = System.currentTimeMillis();
        System.out.println("用时：" + (l2 - l1));
    }

    //@After("xxxx()")
    public void logAfter(JoinPoint point)
    {
        Object[] args = point.getArgs();
        System.out.println("============== after ==============");
    }

    @AfterReturning("xxxx()")
    public void logAfterReturning(JoinPoint point)
    {
        Object[] args = point.getArgs();
        System.out.println("============== afterReturning ==============");
    }

}
