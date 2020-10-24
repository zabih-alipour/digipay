package com.digipay.product.cardmanagmentservice.exceptions;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.PrePersist;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public void handleConflict(Exception e) {
        System.out.println(e.getMessage());
    }
}
