package com.first.spring.refreshtoken;

import java.time.Instant;

import com.first.spring.clientmodule.Client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity(name = "refreshToken")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	private String token;

	@Column(nullable = false)
	private Instant expiryDate;

	@OneToOne
	@JoinColumn(name = "client_id", referencedColumnName = "client_id")
	private Client client;

}