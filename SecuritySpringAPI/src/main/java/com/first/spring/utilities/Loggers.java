package com.first.spring.utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Loggers {
	
	private Loggers() {
	}
	
	public static Logger getControllersLogger() {
		
		return LoggerFactory.getLogger("controller");
	}
	
	public static Logger getDBLogger() {
		
		return LoggerFactory.getLogger("database");
	}
	
}
