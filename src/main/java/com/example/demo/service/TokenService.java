package com.example.demo.service;

import com.example.demo.model.Token;

public interface TokenService {
	Token createToken(Token token);
	
	Token findByToken(String token);
	
	boolean deleteTokenByUser(Long id);
}
