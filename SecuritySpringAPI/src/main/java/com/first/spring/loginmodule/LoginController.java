package com.first.spring.loginmodule;

import java.util.HexFormat;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.first.spring.refreshtoken.RefreshTokenRepo;
import com.first.spring.refreshtoken.RefreshTokenService;
import com.first.spring.utilities.AESEncryptor;
import com.first.spring.utilities.CacheService;
import com.first.spring.utilities.JwtTokenUtil;
//import com.first.spring.utilities.Loggers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;

	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private JwtTokenUtil tokenUtil;
	
	@Autowired
	private AESEncryptor encryptor;
	
	
	
	//private Logger logger = Loggers.getControllersLogger();
	

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Map<String, String> body) {

		String email = body.get("email");
		String password = body.get("password");
		try {
			password = encryptor.decrypt(password);
		} catch (Exception e) {
			//logger.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
		}
		if (email == null || password == null)
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);

		UserDetailsImpl userDetails = loginService.logIn(email, password);
		if (userDetails == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
		}
		
		var userDto = new UserDTO(userDetails);
		
		return ResponseEntity.ok(userDto);
	}
	
	@GetMapping("/match")
	public boolean matchKey(@RequestParam("angSecKey") String angSecKey, @RequestParam("angIv") String angIv){
		
		byte[] secretKey = encryptor.getSecretKey().getEncoded();
		byte[] iv = encryptor.getIv().getIV();
		
		byte[] bytesAngSecKey =  HexFormat.of().parseHex(angSecKey);
		byte[] bytesAngIv =  HexFormat.of().parseHex(angIv);
		
		for(int i = 0 ; i < bytesAngSecKey.length ; i++) {
			if(secretKey[i] != bytesAngSecKey[i])
				return false;
		}
		
		for(int i = 0 ; i < iv.length ; i++) {
			if(bytesAngIv[i] != iv[i])
				return false;
		}
		
		return true;
		
	}
	

	@PostMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		String token = tokenUtil.extractToken(request);
		String username = tokenUtil.extractSubject(token);
		cacheService.evictUserFromCache(username);
		SecurityContextHolder.clearContext();
	}

	
	
	//
	// return ResponseEntity.ok(token);

}
