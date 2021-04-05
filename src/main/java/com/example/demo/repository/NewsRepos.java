package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.News;
import com.querydsl.core.types.Predicate;

@Repository
public interface NewsRepos extends JpaRepository<News, Long>, QuerydslPredicateExecutor<News>{
	
List<News> findAll();
Optional<News> findById(Long id);
void deleteById(Long id);

Optional<News> findOne(Predicate pePredicate);

@Query("SELECT e FROM News e WHERE e.id = :id")
News find(@Param("id") Long id);


}
