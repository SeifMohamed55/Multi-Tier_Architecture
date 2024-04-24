package com.first.spring.clientmodule;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.first.spring.loginmodule.UserDetailsImpl;
import com.first.spring.utilities.Constants;
import com.first.spring.utilities.Loggers;

import jakarta.persistence.EntityNotFoundException;

@Component
public class ClientService {

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private ClientRepository clientRepo;

	private Logger logger = Loggers.getDBLogger();

	public boolean saveStudent(Client client) {
		if (client.hasNullField()) {
			logger.error("Input Error: student fields cannot be null");
			return false;
		}
		int retries = 0;
		while (retries < Constants.MAX_RETRIES) {

			try {
				client.setPassword(encoder.encode(client.getPassword()));
				clientRepo.save(client);
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

	public boolean banClient(Client client) {
		if (client == null) {
			logger.error("Couldn't delete student Null argument exception");
			return false;
		}
		int retries = 0;
		while (retries < Constants.MAX_RETRIES) {

			try {
				client.setBanned(true);
				clientRepo.save(client);
				return true;

			} catch (OptimisticLockingFailureException ex) {
				logger.error(ex.getMessage());
				retries++;

			} catch (IllegalArgumentException ex) {
				logger.error(ex.getMessage());
				return false;
			}
		}
		return false;
	}
	
	public boolean unbanClient(Client client) {
		if (client == null) {
			logger.error("Couldn't delete student Null argument exception");
			return false;
		}
		int retries = 0;
		while (retries < Constants.MAX_RETRIES) {

			try {
				client.setBanned(false);
				clientRepo.save(client);
				return true;

			} catch (OptimisticLockingFailureException ex) {
				logger.error(ex.getMessage());
				retries++;

			} catch (IllegalArgumentException ex) {
				logger.error(ex.getMessage());
				return false;
			}
		}
		return false;
	}


	public ArrayList<UserDetailsImpl> listAllClient() {
		var students = clientRepo.findAll();
		var details = new ArrayList<UserDetailsImpl>();
		for(var student : students) {
			details.add(new UserDetailsImpl(student));
		}
		return details;
	}


	public Client getClientByEmail(String email) {
		Client student;
		try {
			student = clientRepo.findByEmail(email);
		} catch (EntityNotFoundException ex) {
			logger.error(ex.getLocalizedMessage());
			return null;
		}
		return student;
	}

	public Client getClientById(long id) {
		Client student;
		try {
			student = clientRepo.findById(id).get();
		} catch (EntityNotFoundException ex) {
			logger.error(ex.getLocalizedMessage());
			return null;
		} catch (NoSuchElementException ex) {
			logger.error(ex.getLocalizedMessage());
			return null;
		}
		return student;
	}

	

}
