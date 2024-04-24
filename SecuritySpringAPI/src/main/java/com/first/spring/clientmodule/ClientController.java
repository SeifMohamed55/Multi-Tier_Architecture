package com.first.spring.clientmodule;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.first.spring.loginmodule.UserDetailsImpl;
import com.first.spring.utilities.CacheService;
import com.first.spring.utilities.Loggers;


@RestController
@RequestMapping("/client")
public class ClientController {
	Logger logger =  Loggers.getControllersLogger();
	
    @Autowired
    ClientService clientService;
    
    @Autowired
	private CacheService cacheService;
    
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArrayList<UserDetailsImpl>> getStudents(){
    	var lol = clientService.listAllClient();
        return ResponseEntity.ok(lol);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cache/{email}")
    public ResponseEntity<Object> getCachedStudents(@PathVariable("email") String email){
    	var cachedUser = cacheService.getCachedUserByEmail(email);
    	if(cachedUser == null) 
    		return ResponseEntity.notFound().build();
		return ResponseEntity.ok(cachedUser);
    	
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/email/{email}")
    public Client getStudentByEmail(@PathVariable("email") String email){
    	Client student = clientService.getClientByEmail(email);
        return student;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/id/{id}")
    public Client getStudent(@PathVariable("id") String id){
    	long idNum;
    	try {
    		idNum = Long.parseLong(id);
        	Client student = clientService.getClientById(idNum);
        	return student;
		
    	}catch(NumberFormatException ex) {
    		logger.error(ex.getLocalizedMessage());
    	}
    	Client student = clientService.getClientByEmail(id);
		return student;

       
    }





}
