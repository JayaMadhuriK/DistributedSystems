package com.distributedsystems.studentmanagement.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.distributedsystems.studentmanagement.entity.CustomResponse;
import com.distributedsystems.studentmanagement.exception.AlreadyExistsException;
import com.distributedsystems.studentmanagement.exception.EmptyResultException;

@RestControllerAdvice
public class GlobalExceptionHandler {
   
    @ExceptionHandler(EmptyResultException.class)
    public ResponseEntity<CustomResponse> handleEmptyException(EmptyResultException e) {
        CustomResponse customResponse = new CustomResponse(
                404,
                e.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        return new ResponseEntity<>(customResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<CustomResponse> handleEmptyException(AlreadyExistsException e) {
        CustomResponse customResponse = new CustomResponse(
                409,
                e.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        return new ResponseEntity<>(customResponse,HttpStatus.CONFLICT);
    }
}