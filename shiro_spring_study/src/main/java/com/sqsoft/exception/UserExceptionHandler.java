package com.sqsoft.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class UserExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public String String(Exception ex,RedirectAttributes ra) {
		ra.addFlashAttribute("errMessage", ex.getMessage());
		return "redirect:errer";
	}
	
}
