package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private JwtRequestFilter jwtRequestFilter;


	public void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
        .csrf().disable().authorizeRequests()
        .antMatchers("/login","/add").permitAll()
        .antMatchers("/api/getcmt").hasAuthority("ADMIN")
        .anyRequest().authenticated()
		.and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
    }
	

}
