package com.first.spring.loginmodule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.first.spring.authmodule.Role;
import com.first.spring.foodmodule.Food;

public class UserDTO implements Serializable {


	private static final long serialVersionUID = 294906362496683448L;

	private long id;
	
	private String name;
	
	private String email;

	private String token;
	
    private String address;

    private Set<Food> favFood = new HashSet<>();

    private Set<Role> roles = new HashSet<>();
	

	public UserDTO(UserDetailsImpl client) {
		this.email = client.getUsername();
		this.token = client.getToken();
		this.address = client.getAddress();
		copyFoodSet(client.getFavFood());
		this.setRoles(client.getRoles());
		this.setName(client.getFullName());
		this.id = client.getId();
	}
	
	

	
	public UserDTO() {
		
	}

	
	public static List<UserDTO> getListUserDetailsToDTO(List<UserDetailsImpl> clients) {
		var dtos = new ArrayList<UserDTO>();
		for(UserDetailsImpl client : clients) {					
			dtos.add(new UserDTO(client));
		}
		return dtos;
	}

	@Override
	public String toString() {
		return "id: " + id + '\n' + "name: " + name + '\n' + "email: " + email;
	}




	private void copyFoodSet(Set<Food> set) {
		 for(var food : set) {
			 favFood.add(food);
		 }
		 
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Set<Food> getFavFood() {
		return favFood;
	}


	public void setFavFood(Set<Food> favFood) {
		this.favFood = favFood;
	}




	public Set<Role> getRoles() {
		return roles;
	}




	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	



}
