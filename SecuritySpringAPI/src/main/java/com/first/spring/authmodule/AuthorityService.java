package com.first.spring.authmodule;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class AuthorityService {
	
	@Autowired
	private AuthorityRepo authRepo;
		
	public Authority getAuthorityById(long id) {
		Optional<Authority> x = authRepo.findById(id);
		return x.get();
	}
}
