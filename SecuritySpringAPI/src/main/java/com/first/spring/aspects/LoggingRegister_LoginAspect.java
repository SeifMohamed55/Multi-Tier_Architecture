package com.first.spring.aspects;

import org.springframework.http.HttpStatus;

import java.util.Map;


import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.first.spring.loginmodule.UserDTO;

@Aspect
@Component
public class LoggingRegister_LoginAspect {


	private Logger loginLogger = LoggerFactory.getLogger("login");

	private Logger registerLogger = LoggerFactory.getLogger("register");
	
	

	
	
	@Pointcut("execution(* com.first.spring.loginmodule.LoginController.login(..))")
	public void pointCutLoginController() {
	}

	@AfterReturning(pointcut = "pointCutLoginController()", returning = "responseEntity")
	public void logForLoginController(ResponseEntity<Object> responseEntity) {
		UserDTO userDTO;
		switch (responseEntity.getStatusCode()) {
		case HttpStatus.OK:
			userDTO = (UserDTO) responseEntity.getBody();
			loginLogger.info("User Logged Successfully:\n" + userDTO + "\n\n");
			break;

		case HttpStatus.BAD_REQUEST:
			loginLogger.error("BAD REQUEST");
			break;

		case HttpStatus.UNAUTHORIZED:
			Map<String,String> body = (Map<String,String>) responseEntity.getBody();
			String email = body.get("email");
			String pass = body.get("password");
			loginLogger.error("login request with: " + 
					"\nemail: " + email +
					"\npassword: " + pass +
					"\nis UNAUTHORIZED\n\n");
			break;

		default:
			
		}

	}
	
	
	@Pointcut("execution(* com.first.spring.loginmodule.RegisterController.registerUser(..))")
	public void pointCutResigsterController() {
	}

	@AfterReturning(pointcut = "pointCutResigsterController()", returning = "responseEntity")
	public void logForRegisterController(ResponseEntity<Object> responseEntity) {
		UserDTO userDTO;
		switch (responseEntity.getStatusCode()) {
		case HttpStatus.OK:
			userDTO = (UserDTO) responseEntity.getBody();
			registerLogger.info("User Registered Successfully:\n" + userDTO + "\n\n");
			break;

		case HttpStatus.BAD_REQUEST:
			registerLogger.error("BAD REQUEST");
			break;

		case HttpStatus.UNAUTHORIZED:
			registerLogger.error("register request with:\n" + responseEntity.getBody() +
																			"\nis UNAUTHORIZED" + "\n\n");
			break;

		default:
			
		}

	}
	
	
	
	
	@Pointcut("execution(* com.first.spring.clientmodule.AdminController.registerAdmin(..))")
	public void pointCutResigsterAdmin() {
	}

	@AfterReturning(pointcut = "pointCutResigsterAdmin()", returning = "responseEntity")
	public void logForRegisterAdmin(ResponseEntity<Object> responseEntity) {
		UserDTO userDTO;
		switch (responseEntity.getStatusCode()) {
		case HttpStatus.OK:
			userDTO = (UserDTO) responseEntity.getBody();
			registerLogger.info("Admin Registered Successfully:\n" + userDTO + "\n\n");
			break;

		case HttpStatus.BAD_REQUEST:
			registerLogger.error("BAD REQUEST\n");
			break;

		case HttpStatus.UNAUTHORIZED:
			String body = (String) responseEntity.getBody();
			registerLogger.error("login request with:\n" + body + "\nis UNAUTHORIZED" + "\n\n");
			break;

		default:
			
		}

	}
	
	
	
	

}
