package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepos;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ApiApplicationTest {
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	UserService UserService;
	
	@Autowired
	UserRepos userRepos;
	
	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello")));
		
		
	}
	@Test
	public void registrationWorksThroughAllLayers() throws Exception {
		User u = new User("trannhukhanh", "123456", "trannhukhanh@gmail.com", null);
		
		mockMvc.perform(post("/register").contentType("application/json")
				.content(objectMapper.writeValueAsString(u))).andExpect(status().isOk());
		
		User u2 = userRepos.findByUsername("trannhukhanh");
		assertThat(u2.getEmail()).isEqualTo("trannhukhanh@gmail.com");
	}

	   

}
