package com.first.spring.clientmodule;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.spring.loginmodule.UserDTO;
import com.first.spring.utilities.AESEncryptor;
import com.first.spring.utilities.JwtTokenUtil;
//import com.first.spring.utilities.Loggers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("/client")
@RestController
public class ClientController {

	@Autowired
	private AESEncryptor encryptor;
	
	@Autowired
	private ClientService clientServ;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
		
	//private Logger logger = Loggers.getControllersLogger();
	
	@PostMapping("/changePassword")
	public ResponseEntity<Object> changePassword(@RequestBody PasswordDTO entity, HttpServletRequest request, HttpServletResponse response) {
		String token = jwtUtil.extractToken(request);
		String email = jwtUtil.extractSubject(token);
		try {
			
			String oldPass = encryptor.decrypt(entity.getOldPassword());
			String newPass = encryptor.decrypt(entity.getNewPassword());
			var client = clientServ.getClientByEmail(email);
			if(clientServ.passwordMatch(oldPass, client.getPassword()))
				client.setPassword(newPass);
			else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
			clientServ.saveClient(client);
			return ResponseEntity.ok(true);

		} catch (Exception e) {
			//logger.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping("/updateUser")
	public ResponseEntity<Object> updateUser(@RequestBody UserUpdateDTO entity, HttpServletRequest request, HttpServletResponse response) {
		String extractedToken = jwtUtil.extractToken(request);
		String email = jwtUtil.extractSubject(extractedToken);
		try {
			Client client = clientServ.getClientByEmail(email);
			client.setEmail(entity.getEmail());
			client.setAddress(entity.getAddress());
			client.setFirstName(entity.getFirstName());
			client.setLastName(entity.getLastName());
			var savedClientDetails = clientServ.saveClientWithoutChangingPass(client);
			savedClientDetails.setToken(extractedToken);
			return ResponseEntity.ok(new UserDTO(savedClientDetails));
			
		} catch (Exception e) {
			//logger.error(e.getLocalizedMessage());
		}
		return ResponseEntity.badRequest().build();
		
	}
	
	
	
}
