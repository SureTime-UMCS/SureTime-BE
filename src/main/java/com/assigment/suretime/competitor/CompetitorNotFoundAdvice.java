package com.assigment.suretime.competitor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CompetitorNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(CompetitorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String competitorNotFoundHandler(CompetitorNotFoundException ex){
        return ex.getMessage();
    }
}
