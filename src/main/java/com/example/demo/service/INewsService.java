package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.News;
import com.example.demo.model.QNews;
import com.example.demo.repository.NewsRepos;
import com.querydsl.core.types.Predicate;

@Service
public class INewsService implements NewsService{
	@Autowired
	NewsRepos newsrepos;

	@Override
	public List<News> getAllNews() {
		List<News> list = newsrepos.findAll();
		return list;
	}

	@Override
	public News saveNews(News news) {
		// TODO Auto-generated method stub
		News n= newsrepos.save(news);
		return n;
	}

	@Override
	public Optional<News> findById(Long id) {
		// TODO Auto-generated method stub
		Optional<News> n = newsrepos.findById(id);
		return n;
	}

	@Override
	public void deleteById(Long id) {
		newsrepos.deleteById(id);
		
	}

	@Override
	public News findByIdNew(Long id) {
		News n = newsrepos.find(id);
		return n;
	}

	@Override
	public String greet() {
		return "Hello";
	}

	public Predicate searchByUserName() {
		QNews qNew = QNews.news;
		Predicate pre = qNew.content.contains("covid");
		return pre;
	}



}
