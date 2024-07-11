package com.first.spring.aspects;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class RefreshTokenException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public RefreshTokenException(String token, String message) {
    super(String.format("Failed (refreshToken) for [%s]: %s", token, message));
  }
}