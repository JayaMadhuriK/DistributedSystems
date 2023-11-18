package com.distributedsystems.placementmanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException {
    
  public AlreadyExistsException(final String message) {
    super(message);
  }
  
}

