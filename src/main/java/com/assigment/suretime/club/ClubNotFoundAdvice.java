package com.assigment.suretime.club;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ClubNotFoundAdvice<T> {

    @ResponseBody
    @ExceptionHandler(ClubNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String competitorNotFoundHandler(ClubNotFoundException ex){
        return ex.getMessage();
    }
}
