package com.first.spring.refreshtoken;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.first.spring.clientmodule.Client;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
	public Optional<RefreshToken> findByToken(String token);
	
	public Optional<RefreshToken> findByClientId(long clientId);
}
