package com.first.spring.authmodule;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.first.spring.clientmodule.Client;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Authority implements GrantedAuthority {
	
	private static final long serialVersionUID = 8088816845104367210L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="auth_id")
	private long id;
	
	private String authName; // Authority name (e.g., "ROLE_ADMIN", "USER")
	
	@ManyToMany(mappedBy = "authorities")
	@JsonIgnore
	private Set<Client> students = new HashSet<Client>();

	
	public Authority(String authName, long id) {
		this.authName = authName;
		this.id = id;
	}
	
	public Authority() {
		
	}

	// Getters and setters omitted for brevity
	public String getName() {
		return authName.toString();
	}

	public void setName(String authName) {
		this.authName = authName;
	}

	public Set<Client> getUsers() {
		return students;
	}

	public void setUsers(Set<Client> users) {
		this.students = users;
	}

	@Override
	public String getAuthority() {		
		return authName.toString();
	}
	
	public int getAuthId() {
		return (int)id;
	}
	
	 @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true; // Check for reference equality
	        if (obj == null || getClass() != obj.getClass()) return false; // Check for null and type

	        Authority auth = (Authority) obj; // Cast to Student object

	        // Compare relevant fields
	        return this.id == auth.getAuthId();
	    }
	 
}