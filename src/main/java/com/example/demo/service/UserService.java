package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.security.UserPrincipal;

public interface UserService {
	User createUser(User user);
	
	UserPrincipal findByUsername(String username);
	
}
