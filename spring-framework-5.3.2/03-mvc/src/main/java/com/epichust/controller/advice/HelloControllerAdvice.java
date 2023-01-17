package com.epichust.controller.advice;

import com.epichust.entity.User;
import com.epichust.expcetion.UserException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//可以指定对指定包、指定类型生效
//@ControllerAdvice(basePackages = "com.xxxx")

// @ControllerAdvice + @ResponseBody
@RestControllerAdvice

//@ControllerAdvice
public class HelloControllerAdvice {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public String ex(MethodArgumentNotValidException e) {
		StringBuilder sb = new StringBuilder();
		List<ObjectError> allErrors = e.getBindingResult().getAllErrors();

		String message = allErrors.stream().map(s -> s.getDefaultMessage()).collect(Collectors.joining(";"));
		return message;
	}


	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public String ex2(ConstraintViolationException e) {
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		return constraintViolations.stream().map(s -> s.getMessage()).collect(Collectors.joining(";"));
	}

	@ExceptionHandler(UserException.class)
	public ResponseEntity<?> ex3(UserException u) {


		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("{\"name\": \"异常\"}");
	}

}
