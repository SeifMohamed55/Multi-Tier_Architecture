package com.first.spring.loginmodule;


import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.spring.authmodule.AuthorityService;
import com.first.spring.authmodule.Role;
import com.first.spring.clientmodule.Client;
import com.first.spring.clientmodule.ClientService;
import com.first.spring.utilities.Loggers;

@RequestMapping("/register")
@RestController
public class RegisterController {
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private AuthorityService authServ;
	
	private Logger logger = Loggers.getControllersLogger();
	
	

	
	@PostMapping("")
    public ResponseEntity<Object> registerUser(@RequestBody String entity) {
		
		ObjectMapper mapper = new ObjectMapper();
        Client client;
        try {
        	client = mapper.readValue(entity, Client.class);
        	client.getAuthorities().add(authServ.getAuthorityById(Role.ROLE_USER));
            var details = clientService.saveClient(client);
            
            return ResponseEntity.ok("true");
        }catch(DatabindException ex) {
        	logger.error(ex.getLocalizedMessage());
        	
        }catch(JsonProcessingException e){
          logger.error(e.getMessage()); 
        }catch(Exception ex) {
        	logger.error(ex.getLocalizedMessage());
        }
        return ResponseEntity.badRequest().body("Failed To register");
    }
	
	@PostMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> registerAdmin(@RequestBody String entity) {
		
		ObjectMapper mapper = new ObjectMapper();
        Client client;
        try {
        	client = mapper.readValue(entity, Client.class);
        	client.getAuthorities().add(authServ.getAuthorityById(Role.ROLE_USER));
        	client.getAuthorities().add(authServ.getAuthorityById(Role.ROLE_ADMIN));
            var details = clientService.saveClient(client);
            
            return ResponseEntity.ok("true");
        }catch(DatabindException ex) {
        	logger.error(ex.getLocalizedMessage());
        	
        }catch(JsonProcessingException e){
          logger.error(e.getMessage()); 
        }catch(Exception ex) {
        	logger.error(ex.getLocalizedMessage());
        }
        return ResponseEntity.badRequest().body("Bad Request");
    }
	
	
}
	

