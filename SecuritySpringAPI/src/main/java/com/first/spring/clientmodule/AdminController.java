package com.first.spring.clientmodule;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.spring.foodmodule.Food;
import com.first.spring.foodmodule.FoodService;
import com.first.spring.loginmodule.UserDetailsImpl;
import com.first.spring.utilities.CacheService;
import com.first.spring.utilities.Loggers;


@RestController
@RequestMapping("/admin")
public class AdminController {
	Logger logger =  Loggers.getControllersLogger();
	
    @Autowired
    ClientService clientService;
    
    @Autowired
	private CacheService cacheService;
    
    @Autowired
  	private FoodService foodService;
    
    @GetMapping("/all")
    public ResponseEntity<ArrayList<UserDetailsImpl>> getClients(){
    	var lol = clientService.listAllClient();
        return ResponseEntity.ok(lol);
    }
    
    @GetMapping("/cache/{email}")
    public ResponseEntity<Object> getCachedClients(@PathVariable("email") String email){
    	var cachedUser = cacheService.getCachedUserByEmail(email);
    	if(cachedUser == null) 
    		return ResponseEntity.notFound().build();
		return ResponseEntity.ok(cachedUser);
    	
    }
    
    @GetMapping("/email/{email}")
    public Client getClientsByEmail(@PathVariable("email") String email){
    	Client student = clientService.getClientByEmail(email);
        return student;
    }
    
    @GetMapping("/id/{id}")
    public Client getClient(@PathVariable("id") String id){
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
     	
	@PostMapping("/addFood")
	public ResponseEntity<Object> addFood(@RequestBody String map) {

		ObjectMapper mapper = new ObjectMapper();
		Food food;
		try {
			food = mapper.readValue(map, Food.class);
			var details = foodService.save(food);
			return ResponseEntity.ok(details);
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return ResponseEntity.badRequest().body("Food Structure is unaccepted");
		}
	}

	@PostMapping("/hideFood")
	public ResponseEntity<Object> hideFood(@RequestBody String map) {

		ObjectMapper mapper = new ObjectMapper();
		Food food;
		try {
			food = mapper.readValue(map, Food.class);
			food.setHidden(true);
			var details = foodService.save(food);
			return ResponseEntity.ok(details);
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return ResponseEntity.badRequest().body("Food Structure is unaccepted");
		}
	}

	@PostMapping("/unhideFood")
	public ResponseEntity<Object> unhideFood(@RequestBody String map) {

		ObjectMapper mapper = new ObjectMapper();
		Food food;
		try {
			food = mapper.readValue(map, Food.class);
			food.setHidden(false);
			var details = foodService.save(food);
			return ResponseEntity.ok(details);
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			return ResponseEntity.badRequest().body("Food Structure is unaccepted");
		}
	}
	
	
//	@PostMapping("/addAllFood")
//	public ResponseEntity<Object> addAllFood(@RequestBody List<Food> foods) {
//
//		try {
//			for(Food food : foods) {
//				foodService.save(food);
//			}
//			return ResponseEntity.ok("Added Successfully");
//		} catch (Exception ex) {
//			logger.error(ex.getLocalizedMessage());
//			return ResponseEntity.badRequest().body("Food Structure is unaccepted");
//		}
//	}





}
