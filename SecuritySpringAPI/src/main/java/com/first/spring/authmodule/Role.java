package com.first.spring.authmodule;


import org.springframework.security.core.GrantedAuthority;


public class Role implements GrantedAuthority {
	
	private static final long serialVersionUID = -7928652604954132429L;
	
	public static final long ROLE_USER = 1;
	public static final long ROLE_ADMIN = 2;

	private final String authority;	 

    
    public Role(Authority auth){
    	this.authority = auth.getAuthority();
    }
    
    public Role(String auth){
    	this.authority = auth;
    }

	@Override
	public String getAuthority() {
	   return authority;
	}
	
}
