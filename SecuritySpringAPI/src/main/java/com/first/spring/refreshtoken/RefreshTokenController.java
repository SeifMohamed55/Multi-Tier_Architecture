package com.first.spring.refreshtoken;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.first.spring.aspects.RefreshTokenException;
import com.first.spring.loginmodule.UserDetailsImpl;
import com.first.spring.utilities.JwtTokenUtil;

@RestController
public class RefreshTokenController {
	
	@Autowired
	private RefreshTokenService tokenService;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@PostMapping("/refreshToken")
	public JwtResponseDTO refreshToken(@RequestBody Map<String, String> body){
		String refreshTokenRequest = body.get("refreshToken");
	    return tokenService.findByToken(refreshTokenRequest)
	            .map(tokenService::verifyExpiration)
	            .map(RefreshToken::getClient)
	            .map(client -> {
	            	var userDetails =  new UserDetailsImpl(client);
	                String accessToken = jwtUtil.generateAccessToken(userDetails);
	                return JwtResponseDTO.builder()
	                        .accessToken(accessToken)
	                        .token(refreshTokenRequest).build();
	            }).orElseThrow(() ->new RefreshTokenException("","Refresh Token is expired"));
	}
}
