package com.example.demo.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "news")
public class News {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String content;
	
	private String title;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "news")
	private List<Comment> comment; 
	
	
	public News(Long id, String content, String title, List<Comment> comment) {
		super();
		this.id = id;
		this.content = content;
		this.title = title;
		this.comment = comment;
	}

	public News(Long id, String content, List<Comment> comment) {
		super();
		this.id = id;
		this.content = content;
		this.comment = comment;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	public News() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	

}
