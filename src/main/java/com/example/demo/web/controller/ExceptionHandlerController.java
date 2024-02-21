package com.example.demo.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.demo.web.model.ErrorResponse;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorResponse> securityBadResponse(SecurityException ex){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(ex.getLocalizedMessage()));
    }
}
