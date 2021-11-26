package com.ynan.aspect;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * @Author yuannan
 * @Date 2021/11/22 15:57
 */
@Component
@Aspect
public class GrayStreamingListenerAdvice {

    private static final String POINT = "@annotation(com.ynan.aspect.GrayStreamListener)";

    @Around(POINT)
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        GrayStreamListener listener = AnnotationUtils.findAnnotation(method, GrayStreamListener.class);

        int grayType = (int) AnnotationUtils.getValue(listener, "grayType");
        String grayCondition = (String) AnnotationUtils.getValue(listener, "grayCondition");
        String value = (String) AnnotationUtils.getValue(listener, "value");

        return proceedingJoinPoint.proceed();

    }
}
