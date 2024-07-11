package com.first.spring.aspects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class RefreshTokenExceptionHandler {

  @ExceptionHandler(value = RefreshTokenException.class)
  @ResponseStatus(value = HttpStatus.FORBIDDEN)
  public ResponseEntity<Object> handleTokenRefreshException(RefreshTokenException ex, WebRequest request) {
	  return ResponseEntity.status(403).body(ex.getMessage());
  }
}