package com.mx.consultaya.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mx.consultaya.exception.ErrorRestLogin;
import com.mx.consultaya.exception.RequestException;

@RestControllerAdvice
public class ControllerAdvice {
    
    @ExceptionHandler(RequestException.class)
    public ResponseEntity<ErrorRestLogin> requestExceptionHandler(RequestException ex){
        ErrorRestLogin error = ErrorRestLogin.builder().title(ex.getTitle()).severity(ex.getSeverity()).message(ex.getMessage()).build();
        return new ResponseEntity<>(error, ex.getStatus());
    } 
}
