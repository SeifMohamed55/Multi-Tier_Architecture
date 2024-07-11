package com.first.spring.utilities;

import javax.crypto.SecretKey;


public class Constants {
	
	public static final int MAX_RETRIES = 3;
	
	//for jwtToken
	public static final SecretKey SECRET = SecretKeyGenerator.generateSecretKey();
	
	public static final String USER_CACHE_NAME = "userCache";
	
	public static final int JWT_TOKEN_VALIDITY = 20 * 60 * 1000; // 20 minutes
	
	public static String[] PUBLIC_ENDPOINTS = new String[] {
			"/api/food",
			"/login",
			"/register",
	};
	
	public static final long REFRESH_TOKEN_VALIDITY = 2 * 60 * 60 * 1000; // 2 hours


	
}
