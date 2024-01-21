package com.ynan.advisor;

import com.ynan.anno.InterfaceLog;
import java.lang.reflect.Method;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

/**
 * @author yuannan
 */
//@Component
public class LogAdvisor extends StaticMethodMatcherPointcutAdvisor {

	private static final long serialVersionUID = 1l;

	public LogAdvisor() {
		setAdvice(new LogAdvice());
	}

	@Override
	public boolean matches(Method method, Class<?> targetClass) {
		return AnnotationUtils.findAnnotation(method, InterfaceLog.class) != null;
	}
}
