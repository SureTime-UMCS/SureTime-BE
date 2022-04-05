package com.assigment.suretime.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundAuthenticationAdvice {

    @ResponseBody
    @ExceptionHandler(NotFoundAuthenticationExecution.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String notFoundHandler(NotFoundAuthenticationExecution ex) {
        return ex.getMessage();
    }
}
