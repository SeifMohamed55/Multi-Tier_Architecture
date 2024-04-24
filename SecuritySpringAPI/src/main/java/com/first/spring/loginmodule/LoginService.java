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
	private ClientRepository studentRepo;
	
	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private CacheService cacheService;
	
	private Logger logger = Loggers.getDBLogger();
	
	@Cacheable(cacheNames = Constants.USER_CACHE_NAME, key = "#email")
	public UserDetailsImpl logIn(String email, String password) {
		Client student = null;
		int retries = 0;
		while (retries < Constants.MAX_RETRIES) {
			try {
				student = studentRepo.findByEmail(email);
				break;
			} catch (OptimisticLockingFailureException ex) {
				logger.error(ex.getMessage());
				retries++;
			}
		}
		if(student == null)
			return null;
		
		
		if(encoder.matches(password, student.getPassword())) {
			student.getAuthorities().size();
			UserDetailsImpl studentDetailForCache = new UserDetailsImpl(student);
			studentDetailForCache.setToken(jwtUtil.generateAccessToken(studentDetailForCache));
			return studentDetailForCache;
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
				var savedEntity = studentRepo.save(student);
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
	
	private Client getStudentByEmail(String email) {
		Client student;
		try {
			student = studentRepo.findByEmail(email);
		} catch (EntityNotFoundException ex) {
			logger.error(ex.getLocalizedMessage());
			return null;
		}
		return student;
	}

	@Override
	@Cacheable(cacheNames = Constants.USER_CACHE_NAME, key = "#email")
	public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {	
		Client student = getStudentByEmail(email);
		if(student == null)
			throw new UsernameNotFoundException("User not found with email: " + email);
		
		UserDetailsImpl details = new UserDetailsImpl(student);
		return details;
	}
	
	public void logout(UserDetailsImpl details) {
		cacheService.evictUserFromCache(details.getUsername());
	}
	

}
