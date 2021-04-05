package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.NewsDAO;
import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.NewsDTO;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.repository.CommentRepos;
import com.example.demo.repository.NewsRepos;
import com.example.demo.service.CommentService;
import com.example.demo.service.NewsService;
import com.querydsl.core.types.Predicate;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
public class NewsController {
	private static Logger logger = LoggerFactory.getLogger(NewsController.class);
	
	@Autowired 
	NewsService newservice;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	CommentRepos commentRepos;
	
	@Autowired
	NewsDAO dao;

	@Autowired
	NewsRepos newRepos;


@PostMapping(value = "/add")
public ResponseEntity<News> addNew(@RequestBody News news){
	try {
		News n = newservice.saveNews(news);
		return new ResponseEntity<>(n,HttpStatus.CREATED);
	} catch (Exception e) {
		// TODO: handle exception
		return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@PutMapping(value = "/update/{id}")
public ResponseEntity<News> updateNews(@PathVariable Long id, @RequestBody News news){
	
		Optional<News> data = newservice.findById(id);
		if (data.isPresent()) {
			News n = data.get();
			n.setContent(news.getContent());
			logger.info("Update Success");
			return new ResponseEntity<>(newservice.saveNews(n),HttpStatus.OK);
			
		}else {
			logger.error("Update Fail");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	
}

@DeleteMapping(value = "/delete/{id}")
@PreAuthorize("hasAnyAuthority('USER_DELETE')")
public ResponseEntity<News> deleteNews(@PathVariable Long id){
	try {
		newservice.deleteById(id);
		logger.info("Delete Success");
		return new ResponseEntity<>(HttpStatus.OK);
		
	} catch (Exception e) {
		// TODO: handle exception
		logger.error("Delete Fail :" + e);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

@GetMapping(value = "/getnew")
@ResponseBody
public NewsDTO getN(@RequestParam Long id) {
	News n = newservice.findByIdNew(id);
	NewsDTO rs = new NewsDTO(n.getId(), n.getContent());
	return rs;
}

@GetMapping(value = "/getcomment")
public ArrayList<CommentDTO> getComment(@RequestParam Long id) {
	News n = newservice.findByIdNew(id);
	CommentDTO rs = null;
	ArrayList<CommentDTO> list = new ArrayList<>();
	List<Comment> l = commentRepos.findByNewId(n);
	for (Comment comment : l) {
		rs = new CommentDTO(comment.getId(), comment.getComment());
		list.add(rs);
	}
	
	return list;
}

@GetMapping(value = "/")
public @ResponseBody String hello() {
	return newservice.greet();
}


@GetMapping(value = "/content")
public @ResponseBody News query(@RequestParam String content) {
	News n = new News();
	n = dao.findNewsByContent(content);
	return n;
}
@GetMapping(value = "/title")
public @ResponseBody List<News> title(@RequestParam String title){
	List<News> list = dao.findByTitleAsc(title);
	return list;
}

@GetMapping(value = "/findone")
public ResponseEntity<News> findOne(){
	Optional<News> n = null;
	Predicate pre= newservice.searchByUserName();
	n = newRepos.findOne(pre);
	News news = new News();
	news = n.get();
	List<Comment> listC = new ArrayList<Comment>();
	listC = commentRepos.findByNewId(news);
	Comment rs = null;
	ArrayList<Comment> list = new ArrayList<>();
	for (Comment comment : listC) {
		rs = new Comment(comment.getId(), comment.getComment(), null);
		list.add(rs);
	}
	news.setComment(list);
	return new ResponseEntity<News>(news, HttpStatus.OK);
}
}
