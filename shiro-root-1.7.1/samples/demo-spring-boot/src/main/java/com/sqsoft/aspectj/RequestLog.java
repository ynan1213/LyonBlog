//package com.sqsoft.aspectj;
//
//import java.util.Enumeration;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
////@Aspect
////@Component
//public class RequestLog {
//	
//	@Pointcut("execution(public * *..controller.*.*(..))")
//	public void pointcut() {
//		
//	}
//	
//	@Before(value="pointcut()")
//	public void before() {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		String remoteAddr = request.getRemoteAddr();
//		String requestURL = request.getRequestURI().toString();
//		Enumeration<String> parameterNames = request.getParameterNames();
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//		System.out.println("++++++++++++++++地址："+remoteAddr);
//		System.out.println("++++++++++++++++url："+requestURL);
//		System.out.println("++++++++++++++++参数："+parameterNames);
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//	}
//}
