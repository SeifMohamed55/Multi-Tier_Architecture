package com.first.spring.clientmodule;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.first.spring.authmodule.Authority;
import com.first.spring.authmodule.Role;
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

	public UserDetailsImpl saveClient(Client client) {
		if (client.hasNullField()) {
			logger.error("Input Error: student fields cannot be null");
			return null;
		}
		int retries = 0;
		while (retries < Constants.MAX_RETRIES) {

			try {
				client.setPassword(encoder.encode(client.getPassword()));
				Client clientFinale = clientRepo.save(client);
				return new UserDetailsImpl(clientFinale);

			} catch (OptimisticLockingFailureException ex) {
				logger.error(ex.getMessage());
				retries++;

			} catch (IllegalArgumentException ex) {
				logger.error(ex.getMessage());
				return null;

			} catch (Exception ex) {
				logger.error(ex.getMessage());
				return null;
			}
		}

		return null;
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

	public List<UserDetailsImpl> listAllClients() {
		var clientsOpt = clientRepo.findClientsWithoutRole("ROLE_ADMIN");
		if (clientsOpt.isPresent()) {
			var clients = clientsOpt.get();
			var details = new ArrayList<UserDetailsImpl>();
			for (var client : clients) {
				details.add(new UserDetailsImpl(client));
			}
			return details;
		}
		return List.of();
	}

	public List<UserDetailsImpl> listAllAdmins() {
		var clientsOpt = clientRepo.findByAuthorities_AuthNameOrderByFirstNameAscLastNameAsc("ROLE_ADMIN");
		if (clientsOpt.isPresent()) {
			var details = new ArrayList<UserDetailsImpl>();
			var clients = clientsOpt.get();
			for (var client : clients) {
				details.add(new UserDetailsImpl(client));
			}
			return details;
		}
		return List.of();
	}

	public Client getClientByEmail(String email) {
		Client client;
		try {
			client = clientRepo.findByEmail(email);
		} catch (EntityNotFoundException ex) {
			logger.error(ex.getLocalizedMessage());
			return null;
		}
		return client;
	}

	public Client getClientById(long id) {
		Client client;
		try {
			client = clientRepo.findById(id).get();
		} catch (EntityNotFoundException ex) {
			logger.error(ex.getLocalizedMessage());
			return null;
		} catch (NoSuchElementException ex) {
			logger.error(ex.getLocalizedMessage());
			return null;
		}
		return client;
	}
	
	public boolean deleteClientById(long id) {
		Client client = getClientById(id);
		if(client == null)
			return false;
		
		for(Authority auth : client.getAuthorities()) {
			if(auth.getAuthority() == "ROLE_ADMIN")
				return false;
		}
		clientRepo.deleteById(id);
		return true;
	}

}
