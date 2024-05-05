package com.first.spring.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionLoggingForService_Controller {

	
	private Logger controllerLogger = LoggerFactory.getLogger("controller");

	private Logger dbLogger = LoggerFactory.getLogger("database");
	
	
	@Pointcut("@annotation(org.springframework.stereotype.Service) || @annotation(org.springframework.stereotype.Component)")
	public void pointCutServiceComponent() {
	}

	@AfterThrowing(pointcut = "pointCutServiceComponent()", throwing = "ex")
	public void logErrorForDatabase(JoinPoint joinPoint, Exception ex) {
		dbLogger.error("An error occurred during : " + joinPoint.getKind() +'\n' +
						joinPoint.getSignature().toShortString() + '\n' + 
						"With Exception: " + ex.getLocalizedMessage());

	}
	
	

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RestController)")
	public void pointCutRestController() {
	}

	@AfterThrowing(pointcut = "pointCutRestController()", throwing = "ex")
	public void logErrorForControllers(JoinPoint joinPoint, Exception ex) {
		controllerLogger.error("An error occurred during : " + joinPoint.getKind() +'\n' +
				joinPoint.getSignature().toShortString() + '\n' + 
				"With Exception: " + ex.getLocalizedMessage());

	}
}
