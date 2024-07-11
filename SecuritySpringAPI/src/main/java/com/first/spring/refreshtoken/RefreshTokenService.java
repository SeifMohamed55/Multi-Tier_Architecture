package com.first.spring.refreshtoken;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.first.spring.aspects.RefreshTokenException;
import com.first.spring.clientmodule.Client;
import com.first.spring.clientmodule.ClientRepository;
import com.first.spring.utilities.Constants;

@Service
public class RefreshTokenService {


	@Autowired
	private RefreshTokenRepo tokenRepo;

	public Optional<RefreshToken> getRefreshToken(String token) {
		return tokenRepo.findByToken(token);

	}


	public RefreshToken createRefreshToken(Client client) {
		Optional<RefreshToken> refTok = tokenRepo.findByClientId(client.getId());
		if (refTok.isPresent()) {
			var token = verifyExpiration(refTok.get());
			if(token != null)
				return token;
		}

		RefreshToken refreshToken = RefreshToken.builder()
				.expiryDate(Instant.now().plusMillis(Constants.REFRESH_TOKEN_VALIDITY))
				.token(UUID.randomUUID().toString()).client(client).build();

		try {
			refreshToken = tokenRepo.save(refreshToken);
			return refreshToken;
		} catch (Exception ex) {
			throw new RefreshTokenException("", "couldn't create RefreshToken");
		}

	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().isBefore(Instant.now())) {
			tokenRepo.delete(token);
			return null;
		}

		return token;
	}

	public Optional<RefreshToken> findByToken(String refreshToken) {
		return tokenRepo.findByToken(refreshToken);
	}
}
