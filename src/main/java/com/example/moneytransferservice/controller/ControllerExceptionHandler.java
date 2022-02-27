package com.example.moneytransferservice.controller;

import com.example.moneytransferservice.dto.ResponseWithError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    ResponseWithError handleBindException(BindException e) {
        logger.error("BindException: " + e.getMessage());
        return new ResponseWithError(e.getMessage(), -1);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    ResponseWithError handleRuntimeException(RuntimeException e) {
        logger.error("Server exception: " + e.getMessage());
        return new ResponseWithError(e.getMessage(), -1);
    }
}