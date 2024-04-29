package com.first.spring.loginmodule;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.first.spring.clientmodule.Client;
import com.first.spring.clientmodule.ClientRepository;
import com.first.spring.utilities.CacheService;
import com.first.spring.utilities.Constants;
import com.first.spring.utilities.JwtTokenUtil;
import com.first.spring.utilities.Loggers;

import jakarta.persistence.EntityNotFoundException;

@Component
public class LoginService implements UserDetailsService  {

	@Autowired
	private ClientRepository clientRepo;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private CacheService cacheService;
	
	private Logger logger = Loggers.getDBLogger();
	
	public UserDetailsImpl logIn(String email, String password) {
		Client client = null;
		int retries = 0;
		while (retries < Constants.MAX_RETRIES) {
			try {
				client = clientRepo.findByEmail(email);
				break;
			} catch (OptimisticLockingFailureException ex) {
				logger.error(ex.getMessage());
				retries++;
			}
		}
		if(client == null)
			return null;
		
		
		if(encoder.matches(password, client.getPassword())) {
			client.getAuthorities().size();
			UserDetailsImpl clientDetails = saveToCache(client);
			clientDetails.setToken(jwtUtil.generateAccessToken(clientDetails));
			return clientDetails;
		}
		return null;
	}
	
	
	public boolean register(Client student) {
		if(student == null) return false;
		
		if (student.hasNullField()) {
			logger.error("Input Error: student fields cannot be null");
			return false;
		}
		int retries = 0;
		while (retries < Constants.MAX_RETRIES) {

			try {
				student.setPassword(encoder.encode(student.getPassword()));
				var savedEntity = clientRepo.save(student);
				logger.info("student: " + savedEntity.toString());
				return true;

			} catch (OptimisticLockingFailureException ex) {
				logger.error(ex.getMessage());
				retries++;

			} catch (IllegalArgumentException ex) {
				logger.error(ex.getMessage());
				return false;

			} catch (Exception ex) {
				logger.error(ex.getMessage());
				return false;
			}
		}

		return false;
	}
	
	@Cacheable(cacheNames = Constants.USER_CACHE_NAME, key = "#client.email")
	private UserDetailsImpl saveToCache(Client client) {
		
		var clientDetails = new UserDetailsImpl(client);
		cacheService.putUserDetailsInCache(clientDetails);
		return clientDetails;
		 
	}

	@Override
	@Cacheable(cacheNames = Constants.USER_CACHE_NAME, key = "#email")
	public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {	
		Client client = clientRepo.findByEmail(email);
		if(client == null)
			throw new UsernameNotFoundException("User not found with email: " + email);
		
		UserDetailsImpl details = new UserDetailsImpl(client);
		return details;
	}
	
	public void logout(UserDetailsImpl details) {
		cacheService.evictUserFromCache(details.getUsername());
	}
	

}
