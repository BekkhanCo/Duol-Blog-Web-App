package com.example.appduol.service;

import com.example.appduol.model.entity.Blog;
import com.example.appduol.model.entity.Comment;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface CommentService {

  Optional<Comment> getById(Long id);

  Collection<Comment> getAll();

  Collection<Comment> getAllByFilter(Boolean checked);

  Comment save(Comment comment);

  void delete(Comment comment);

  Comment update(Long id, Comment comment);
}
