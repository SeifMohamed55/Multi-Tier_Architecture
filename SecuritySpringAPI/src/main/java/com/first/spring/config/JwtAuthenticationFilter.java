package com.first.spring.config;

import java.io.IOException;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.first.spring.authmodule.Role;
import com.first.spring.refreshtoken.RefreshTokenService;
import com.first.spring.utilities.CacheService;
import com.first.spring.utilities.Constants;
import com.first.spring.utilities.JwtTokenUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private JwtTokenUtil jwtUtil;
	private CacheService cacheService;

	public JwtAuthenticationFilter(JwtTokenUtil jwtUtil, CacheService cacheService) {
		this.jwtUtil = jwtUtil;
		this.cacheService = cacheService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = jwtUtil.extractToken(request);
		String path = request.getServletPath();
		boolean needsAuthentication = true;
		for (String publicEndPoint : Constants.PUBLIC_ENDPOINTS) {
			if (path.contains(publicEndPoint)) {
				needsAuthentication = false;
				break;
			}
		}
		if (needsAuthentication) {
			// Validate token
			if (token != null) {
				try {
					Claims claims = jwtUtil.getAllClaimsFromToken(token);
					String email = claims.get("email", String.class);

					@SuppressWarnings("unchecked")
					Collection<LinkedHashMap<String, String>> authoritiesClaim = (Collection<LinkedHashMap<String, String>>) claims
							.get("roles");
					ArrayList<Role> authorities = new ArrayList<>();
					for (LinkedHashMap<String, String> confusion : authoritiesClaim) {
						authorities.add(new Role(confusion.get("authority")));
					}
					
					Date expiration = claims.getExpiration();
					if (expiration.before(new Date())) {
						// Handle token expiration
						cacheService.evictUserFromCache(email);
						throw new JwtException("Token has expired");
					}
					Authentication authentication = new UsernamePasswordAuthenticationToken(email, "", authorities);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}

				catch (JwtException e) {
					// Token validation failed
					logger.error(e.getLocalizedMessage());
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
				} catch (Exception ex) {
					logger.error(ex.getLocalizedMessage());
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
				}

			}
		}
		filterChain.doFilter(request, response);
	}

}