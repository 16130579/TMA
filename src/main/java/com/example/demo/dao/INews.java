package com.example.demo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.example.demo.model.News;
import com.example.demo.model.QNews;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class INews implements NewsDAO {
	
	@PersistenceContext
	private EntityManager em;
	

	@Override
	public News findNewsByContent(String content) {
		// TODO Auto-generated method stub
		JPAQuery<News> query =new JPAQuery<>(em);
		QNews news = QNews.news;
		
		return query.from(news).where(news.content.eq(content)).fetchFirst();
	}

	@Override
	public List<News> findByTitleAsc(String title) {
		// TODO Auto-generated method stub
		JPAQuery<News> query = new JPAQuery<>(em);
		QNews news = QNews.news;
		return query.from(news).where(news.title.eq(title)).orderBy(news.id.asc()).fetch();
	}
	
	

}
	
	
