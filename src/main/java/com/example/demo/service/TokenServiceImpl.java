package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Token;
import com.example.demo.repository.TokenRepos;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepos tokenRepository;

    public Token createToken(Token token){
        return tokenRepository.saveAndFlush(token);
    }

	@Override
	public Token findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

	@Override
	public boolean deleteTokenByUser(Long id) {
		// TODO Auto-generated method stub
		if (id != null) {
			Token token =tokenRepository.findByCreatedBy(id);
			tokenRepository.delete(token);
			return true;
		}else {
			return false;
		}
		
	}
}
