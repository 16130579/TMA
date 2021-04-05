package com.example.demo;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.controller.NewsController;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.repository.CommentRepos;
import com.example.demo.security.JwtRequestFilter;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.CommentService;
import com.example.demo.service.NewsService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(NewsController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TestNewsController {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private NewsService newsService;

	@MockBean
	private CommentService cmt;

	@MockBean
	private CommentRepos cmtt;

	@MockBean
	private JwtUtil jwtUtil;

	@MockBean
	private JwtRequestFilter jwt;

	@Test
	public void greetingShouldReturnMessageFromService() throws Exception {
		when(newsService.greet()).thenReturn("Hello");
//	given(newsService.greet()).willReturn("Hello");
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(is("Hello")));
	}

	@Test
	public void testGet() throws Exception {
		List<Comment> allTodos = new ArrayList<Comment>();
		News n = null;
		Comment c = new Comment(1L, "a", n);
		Comment c1 = new Comment(2L, "b", n);
		allTodos.add(c);
		allTodos.add(c1);

		given(cmt.findAll()).willReturn(allTodos);

		mockMvc.perform(get("/api/getcmt")).andDo(print()) // Thực hiện GET REQUEST
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()) // Mong muốn
																											// Server
																											// trả về
																											// status
																											// 200
				.andExpect(jsonPath("$[0].id", is(1))) // Hi vọng phần tử trả về đầu tiên có id = 1
				.andExpect(jsonPath("$[0].comment", is("a"))); // Hi vọng phần tử trả về đầu tiên có title = "a"
//     .andExpect(jsonPath("$[0].news", is(null)));

	}

	@Test
	public void testPost() throws Exception {
		News n = new News(5L, "a", null);
		ObjectMapper object = new ObjectMapper();
		mockMvc.perform(post("/add").contentType(MediaType.APPLICATION_JSON).content(object.writeValueAsString(n)))
				.andDo(print()).andExpect(status().isCreated());

	}

	@Test
	public void testPut() throws Exception {
		News n = new News(5L, "a", null);
		Optional<News> t = Optional.ofNullable(n);
		ObjectMapper object = new ObjectMapper();
		when(newsService.findById(5L)).thenReturn(t);
		mockMvc.perform(put("/update/{id}",5).contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
				.content(object.writeValueAsString(n)))
				.andExpect(status().isOk()).andReturn();
	}

	@WithMockUser(authorities = "USER_DELETE")
	@Test
	public void testDelete() throws Exception {
		mockMvc.perform(delete("/delete/{id}",5)).andExpect(status().isOk());
	}
	@Test
	public void testNews() throws Exception {
		News n = new News(1L, "thanh", null);
		when(newsService.findByIdNew(1L)).thenReturn(n);
		mockMvc.perform(get("/getnew").param("id", "1")).andExpect(status().isOk()).andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.content", is("thanh"))).andReturn();
		
	}
}
