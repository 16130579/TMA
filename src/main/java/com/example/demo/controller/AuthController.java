package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepos;
import com.example.demo.security.JwtUtil;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.TokenService;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class AuthController {

	@Autowired
	UserService userService;
	
	@Autowired
	TokenService tokenService;
	
	@Autowired
    private JwtUtil jwtUtil;

	@Autowired
	UserRepos userRepos;
	
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		User u = userRepos.findByUsername(user.getUsername());
		if (u != null) {
			return null;
		}else {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		return userService.createUser(user);
		}
	}
	
	@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        UserPrincipal userPrincipal = userService.findByUsername(user.getUsername());
        if (null == user || !new BCryptPasswordEncoder().matches(user.getPassword(), userPrincipal.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("tài khoản hoặc mật khẩu không chính xác");
        }
        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        token.setCreatedBy(userPrincipal.getUserId());
        tokenService.createToken(token);
        UserPrincipal rs = new UserPrincipal(userPrincipal.getUserId(), userPrincipal.getUsername(), token.getToken(), userPrincipal.getPassword(), userPrincipal.getAuthorities());
        return ResponseEntity.ok(rs);
        
    }
	
	@GetMapping("/current")
	public @ResponseBody String logout() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		    UserPrincipal userPrincipal = (UserPrincipal) principal;
		    return userPrincipal.getUsername();
		}else {
			return "0";
		}
	}
	
	@GetMapping("/logoutSuccessful")
	public @ResponseBody String logSuccess() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		    UserPrincipal userPrincipal = (UserPrincipal) principal;
		    Long id= userPrincipal.getUserId();
		    tokenService.deleteTokenByUser(id);
		    return "Logout Success" ;
		}else {
			return "Logout Fail";
		}
		
	}
	
	@GetMapping("/hello")
    @PreAuthorize("hasAnyAuthority('USER_READ')")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<String>("Hello World!", HttpStatus.OK);
    }
	
	@GetMapping("/testuser")
	public String find(@RequestBody User user){
		UserPrincipal userPrincipal = userService.findByUsername(user.getUsername());
		return userPrincipal.getAuthorities().toString();
	}
}
