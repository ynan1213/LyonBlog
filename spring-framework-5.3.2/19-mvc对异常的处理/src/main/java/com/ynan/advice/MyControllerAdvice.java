package com.ynan.advice;

import com.ynan.exception.MyException;
import com.ynan.exception.MyRuntimeException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.sql.SQLException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author yuannan
 * @Date 2021/12/27 12:01
 */
@RestControllerAdvice
public class MyControllerAdvice {

	/**
	 * 注解上的优先级高一些
	 */
	@ExceptionHandler({MyException.class, SQLException.class})
	public String xxx(Exception e) {
		Throwable throwable = getRoot(e);
		return "出现了 MyException 异常 ：" + throwable.getMessage();
	}

	@ExceptionHandler(MyRuntimeException.class)
	public String yyy(Exception e) {
		Throwable throwable = getRoot(e);
		return "出现了 MyRuntimeException 异常 ：" + throwable.getMessage();
	}

	private Throwable getRoot(Throwable throwable) {
		if (throwable instanceof UndeclaredThrowableException) {
			return getRoot(throwable.getCause());
		}
		if (throwable instanceof InvocationTargetException) {
			return getRoot(throwable.getCause());
		}
		return throwable;
	}
}