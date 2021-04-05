package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.News;

public interface NewsDAO {
	public News findNewsByContent(String content);
	
	public List<News> findByTitleAsc(String title);
	
}
