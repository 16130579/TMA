package com.example.demo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepos;
import com.example.demo.security.UserPrincipal;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepos userRepos;

	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
		return userRepos.saveAndFlush(user);
	}

	@Override
	public UserPrincipal findByUsername(String username) {
        User user = userRepos.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal();
        if (null != user) {
            Set<String> authorities = new HashSet<>();
            if (null != user.getRoles()) user.getRoles().forEach(r -> {
                authorities.add(r.getRoleKey());
                r.getPermissions().forEach(p -> authorities.add(p.getPermissionKey()));
            });

            userPrincipal.setUserId(user.getId());
            userPrincipal.setUsername(user.getUsername());
            userPrincipal.setPassword(user.getPassword());
            userPrincipal.setAuthorities(authorities);
        }
        return userPrincipal;
    }

}
