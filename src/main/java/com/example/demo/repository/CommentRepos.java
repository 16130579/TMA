package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Comment;
import com.example.demo.model.News;

public interface CommentRepos extends CrudRepository<Comment, Long> {
	
	@Query("SELECT e FROM Comment e WHERE e.news= :new")
	List<Comment> findByNewId(@Param("new") News news);
	
	List<Comment> findBynews(News n);
	
	List<Comment> findAll();
	
	Optional<Comment> findById(Long id);
	
}
