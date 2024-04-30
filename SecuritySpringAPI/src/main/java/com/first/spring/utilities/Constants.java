package com.first.spring.utilities;

import javax.crypto.SecretKey;


public class Constants {
	
	public static final int MAX_RETRIES = 3;
	
	//for jwtToken
	public static final SecretKey SECRET = SecretKeyGenerator.generateSecretKey();
	
	public static final String USER_CACHE_NAME = "userCache";
	
	public static final int VALIDITY = 5 * 60 * 60 * 1000;
	
	public static String[] PUBLIC_ENDPOINTS = new String[] {
			"/api/food",
			"/login",
			"/register",
	};


	
}
