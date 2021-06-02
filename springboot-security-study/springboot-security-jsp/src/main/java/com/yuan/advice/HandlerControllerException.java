package com.epichust.advice;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerControllerException
{
    @ExceptionHandler(RuntimeException.class)
    public String handler(RuntimeException e)
    {
        if (e instanceof AccessDeniedException)
        {
            return "redirect:/403.jsp";
        } else
        {
            return "redirect:/500.jsp";
        }
    }

}
