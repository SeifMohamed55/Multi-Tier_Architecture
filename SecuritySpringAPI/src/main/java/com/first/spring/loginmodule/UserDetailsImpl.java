package com.first.spring.loginmodule;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.first.spring.authmodule.Authority;
import com.first.spring.authmodule.Role;
import com.first.spring.clientmodule.Client;
import com.first.spring.foodmodule.Food;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 3316716547427266950L;

	private long id;
	
	private String name;
	
	private String email;

	@JsonIgnore
	private String password;

	@JsonIgnore
	private Set<Role> authorities = new HashSet<Role>();

	private String token;

	@JsonIgnore
	private boolean isTokenExpired = false;
	
    private String address;

    private Set<Food> favFood = new HashSet<>();

	

	public UserDetailsImpl(Client client) {
		this.email = client.getEmail();
		this.password = client.getPassword();		
		copySet(client.getAuthorities());
		token = null;
		this.setAddress(client.getAddress());
		copyFoodSet(client.getFavFood());
		this.name = client.getFirstName() + " " + client.getLastName();
		this.id = client.getId();
	}

	public Set<Food> getFavFood() {
		return favFood;
	}

	public void setFavFood(Set<Food> favFood) {
		this.favFood = favFood;
	}

	private void copySet(Set<Authority> set) {
		 for(var authority : set) {
			 authorities.add(new Role(authority));		
		 }
		 
	}
	
	private void copyFoodSet(Set<Food> set) {
		 for(var food : set) {
			 favFood.add(food);
		 }
		 
	}
	

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;		
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !isTokenExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getToken() {
		if (isTokenExpired)
			return null;
		return token;
	}

	public void setToken(String token) {
		this.token = token;
		isTokenExpired = false;
	}
	
	public void expireToken() {
		this.isTokenExpired = true;
	}

	
	@Override
	public String toString() {
		String authority = "";
		for(Role auth : authorities) {
			authority += auth.getAuthority();
		}
		return "Email: "+ email + "\nAuthorities: " + authority;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFullName() {
		return name;
	}

	public void setFullName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
