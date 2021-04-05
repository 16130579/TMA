package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Comment;
import com.example.demo.repository.CommentRepos;

@Service
public class ICommentService implements CommentService {
	@Autowired
	CommentRepos commentRepos;

	@Override
	public List<Comment> findAll() {
		List<Comment> list = commentRepos.findAll();
		List<Comment> rs = new ArrayList<Comment>();
		Comment cmt = new Comment();
		for (Comment comment : list) {
			cmt.setId(comment.getId());
			cmt.setComment(comment.getComment());
			rs.add(cmt);
		}
		return rs;
	}

}
