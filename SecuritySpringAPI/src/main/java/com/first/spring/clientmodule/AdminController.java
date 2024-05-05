package com.first.spring.clientmodule;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.first.spring.authmodule.AuthorityService;
import com.first.spring.authmodule.Role;
import com.first.spring.foodmodule.Food;
import com.first.spring.foodmodule.FoodService;
import com.first.spring.loginmodule.UserDTO;
import com.first.spring.utilities.AESEncryptor;
import com.first.spring.utilities.CacheService;
//import com.first.spring.utilities.Loggers;


@RestController
@RequestMapping("/admin")
public class AdminController {
	//Logger logger =  Loggers.getControllersLogger();
	
    @Autowired
    private ClientService clientService;
    
    @Autowired
	private CacheService cacheService;
    
    @Autowired
  	private FoodService foodService;
    
    @Autowired
    private AuthorityService authServ;
    
    @Autowired
    private AESEncryptor encryptor;
    
    @GetMapping("/allClients")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getClients(){
    	var clients  = clientService.listAllClients();
    	
        return ResponseEntity.ok(UserDTO.getListUserDetailsToDTO(clients));
    }
    
    @GetMapping("/allAdmins")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getAdmins(){
    	var admins  = clientService.listAllAdmins();
    	
        return ResponseEntity.ok(UserDTO.getListUserDetailsToDTO(admins));
    }
    

	@PostMapping("/registration")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> registerAdmin(@RequestBody String entity) {
		
		ObjectMapper mapper = new ObjectMapper();
        Client client;
        String decryptedPassword;
        try {
        	client = mapper.readValue(entity, Client.class);
        	client.getAuthorities().add(authServ.getAuthorityById(Role.ROLE_USER));
        	client.getAuthorities().add(authServ.getAuthorityById(Role.ROLE_ADMIN));
        	try {
        		decryptedPassword = encryptor.decrypt(client.getPassword());
        		client.setPassword(decryptedPassword);
    		} catch (Exception e) {
    			//logger.error(e.getLocalizedMessage());
    			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(entity);
    		}
            var details = clientService.saveClient(client);
            
            return ResponseEntity.ok(new UserDTO(details));
        }catch(DatabindException ex) {
        	//logger.error(ex.getLocalizedMessage());
        	
        }catch(JsonProcessingException e){
          //logger.error(e.getMessage()); 
        }catch(Exception ex) {
        	//logger.error(ex.getLocalizedMessage());
        }
        return ResponseEntity.badRequest().body("Bad Request");
    }
    
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> deleteClient(@PathVariable("id") String strId){
		long id;
		try {
			id = Long.parseLong(strId);
			var deleted = clientService.deleteClientById(id);
			return ResponseEntity.ok(deleted);
		}catch(Exception ex) {
			//logger.error(ex.getLocalizedMessage());
			return ResponseEntity.badRequest().build();
		}
	}
	
    @GetMapping("/cache/{email}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> getCachedClients(@PathVariable("email") String email){
    	var cachedUser = cacheService.getCachedUserByEmail(email);
    	if(cachedUser == null) 
    		return ResponseEntity.notFound().build();
		return ResponseEntity.ok(cachedUser);
    	
    }
    
    @GetMapping("/email/{email}")
	@PreAuthorize("hasRole('ADMIN')")
    public Client getClientsByEmail(@PathVariable("email") String email){
    	Client student = clientService.getClientByEmail(email);
        return student;
    }
    
    @GetMapping("/id/{id}")
	@PreAuthorize("hasRole('ADMIN')")
    public Client getClient(@PathVariable("id") String id){
    	long idNum;
    	try {
    		idNum = Long.parseLong(id);
        	Client student = clientService.getClientById(idNum);
        	return student;
		
    	}catch(NumberFormatException ex) {
    		//logger.error(ex.getLocalizedMessage());
    	}
    	Client student = clientService.getClientByEmail(id);
		return student;
       
    }
     	
	@PostMapping("/addFood")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> addFood(@RequestBody String map) {

		ObjectMapper mapper = new ObjectMapper();
		Food food;
		try {
			food = mapper.readValue(map, Food.class);
			var details = foodService.save(food);
			return ResponseEntity.ok(details);
		} catch (Exception ex) {
			//logger.error(ex.getLocalizedMessage());
			return ResponseEntity.badRequest().body("Food Structure is unaccepted");
		}
	}

	@PostMapping("/hideFood")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> hideFood(@RequestBody String map) {

		ObjectMapper mapper = new ObjectMapper();
		Food food;
		try {
			food = mapper.readValue(map, Food.class);
			food.setHidden(true);
			foodService.save(food);
			return ResponseEntity.ok(true);
		} catch (Exception ex) {
			//logger.error(ex.getLocalizedMessage());
			return ResponseEntity.badRequest().body("Food Structure is unaccepted");
		}
	}

	@PostMapping("/unhideFood")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> unhideFood(@RequestBody String map) {

		ObjectMapper mapper = new ObjectMapper();
		Food food;
		try {
			food = mapper.readValue(map, Food.class);
			food.setHidden(false);
			foodService.save(food);
			return ResponseEntity.ok(true);
		} catch (Exception ex) {
			//logger.error(ex.getLocalizedMessage());
			return ResponseEntity.badRequest().body("Food Structure is unaccepted");
		}
	}
	
	
	@PutMapping("/updateFood")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateFood(@RequestBody String entity){
		ObjectMapper mapper = new ObjectMapper();
		try {
			var food = mapper.readValue(entity, Food.class);
			if(food.getPrice() > 0)
				return ResponseEntity.ok(foodService.save(food));
		}catch(Exception ex) {
			//logger.error(ex.getMessage());
		}
		
		return ResponseEntity.badRequest().body("Price is negative");
	}
	
	
	@GetMapping("/allFood")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> getAllFood() {
		var foods = foodService.findAll();
		return ResponseEntity.ok(foods);

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
