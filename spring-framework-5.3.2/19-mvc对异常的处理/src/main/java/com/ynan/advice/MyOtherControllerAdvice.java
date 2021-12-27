package com.ynan.advice;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author yuannan
 * @Date 2021/12/27 15:25
 */
//可以指定对指定包、指定类型生效
@ControllerAdvice(basePackages = "com.xxxx")
public class MyOtherControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public String ex(MethodArgumentNotValidException e) {
		StringBuilder sb = new StringBuilder();
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();

		String message = allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(";"));
		return message;
	}


}
