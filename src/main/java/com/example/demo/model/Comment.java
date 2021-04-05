package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column
private String comment;

private Date createDate;

@ManyToOne
private News news;

public Long getId() {
	return id;
}

public void setId(Long id) {
	this.id = id;
}

public String getComment() {
	return comment;
}

public void setComment(String comment) {
	this.comment = comment;
}

public News getNews() {
	return news;
}

public void setNews(News news) {
	this.news = news;
}

public Comment(Long id, String comment, News news) {
	super();
	this.id = id;
	this.comment = comment;
	this.news = news;
}

public Comment() {
	super();
}

public Date getCreateDate() {
	return createDate;
}

public void setCreateDate(Date createDate) {
	this.createDate = createDate;
}


}
