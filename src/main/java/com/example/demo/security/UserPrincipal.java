package com.example.demo.security;

import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;


public class UserPrincipal implements UserDetails{

	private Long userId;
	private String token;
	    private String username;
	    private String password;
	    private Collection authorities;

	@Override
	public Collection getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
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

	public UserPrincipal(Long userId, String username, String token, String password, Collection authorities) {
		super();
		this.userId = userId;
		this.username = username;
		this.token = token;
		this.password = password;
		this.authorities = authorities;
	}

	public UserPrincipal() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public void setAuthorities(Collection authorities) {
		this.authorities = authorities;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

}
