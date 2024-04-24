package com.first.spring.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.first.spring.loginmodule.UserDetailsImpl;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenUtil {

	private final SecretKey SECRET = Constants.SECRET; 
    private final int JWT_TOKEN_VALIDITY = Constants.VALIDITY; // 5 hours in ms (customizable)
    

    
    public String generateAccessToken(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userDetails.getUsername());
        claims.put("roles", userDetails.getAuthorities());
        claims.put("address", userDetails.getAddress());

        Date issuedAt = new Date();
        Date expirationDate = new Date(issuedAt.getTime() + JWT_TOKEN_VALIDITY );


        return Jwts.builder()
                .claims(claims)
                .issuedAt(issuedAt)
                .expiration(expirationDate)
                .signWith(SECRET)
                .subject(userDetails.getUsername())
                .compact();
    }
    

    public String extractToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
    
       
    public Claims getAllClaimsFromToken(String token) throws JwtException {
        return Jwts.parser().verifyWith(Constants.SECRET).build().parseSignedClaims(token).getPayload();
    }

    public String extractSubject(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    public String extractIssuer(String token) {
        return getAllClaimsFromToken(token).getIssuer();
    }

    public Long extractExpiration(String token) {
        return getAllClaimsFromToken(token).getExpiration().getTime();
    }

        
    


}