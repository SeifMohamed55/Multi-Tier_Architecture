package com.first.spring.utilities;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;

public class SecretKeyGenerator {

	public static SecretKey generateSecretKey() {
	          
        SecretKey secretKeyObj = Jwts.SIG.HS256.key().build();      	
		return secretKeyObj;
		
	}
}
