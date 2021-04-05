package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.News;
import com.querydsl.core.types.Predicate;

public interface NewsService {
	public List<News> getAllNews();

	public News saveNews(News news);

	public Optional<News> findById(Long id);

	public void deleteById(Long id);

	public News findByIdNew(Long id);
	
	public String greet();
	
	public Predicate searchByUserName();
	
//	public News findOne(Predicate predicate);

}
