package com.first.spring.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.first.spring.loginmodule.LoginService;

@Configuration
@EnableWebSecurity
@EnableCaching
@EnableMethodSecurity
public class SecurityConfig  {
	
	@Autowired
	private LoginService loginService;
		
	
	// Adding Let's Encrypt certificate instead of self signed cert OR Cloudflare free
	// csrf importance
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				//.requiresChannel(https -> https.anyRequest().requiresSecure())
				.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(cust -> cust						
						.requestMatchers("/login").permitAll()
						.requestMatchers("/register").permitAll()
						.requestMatchers("/match").permitAll()
						.requestMatchers("/api/food/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.anyRequest().authenticated())
				.userDetailsService(loginService)
				.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement(ses -> ses.sessionCreationPolicy(SessionCreationPolicy.STATELESS))				
				.formLogin(auth -> auth.disable())
				.logout(log -> log.disable());
				

		return http.build();
	}



}
