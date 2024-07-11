package com.first.spring.aspects;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import io.jsonwebtoken.JwtException;

@RestControllerAdvice
public class JwtExceptionHandler {

	@ExceptionHandler(value = JwtException.class)
	  @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	  public ResponseEntity<Object> handleTokenRefreshException(JwtException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("JWT token expired");
	  }

}
