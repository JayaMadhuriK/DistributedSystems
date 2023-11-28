package com.distributedsystems.placementmanagement.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.distributedsystems.placementmanagement.entity.CustomResponse;
import com.distributedsystems.placementmanagement.exception.AlreadyExistsException;
import com.distributedsystems.placementmanagement.exception.EmptyResultException;

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
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomResponse> handleRuntimeException(RuntimeException e) {
        CustomResponse customResponse = new CustomResponse(
                400,
                e.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        return new ResponseEntity<>(customResponse,HttpStatus.BAD_REQUEST);
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
