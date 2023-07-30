package com.example.appduol.service;

import com.example.appduol.model.entity.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public interface CommentService {

  @Transactional
  Optional<Comment> getById(Long id);

  @Transactional
  Collection<Comment> getAll();

  @Transactional
  Collection<Comment> getAllByFilter(Boolean checked,Long blogId);

  Comment save(Comment comment);

  void delete(Comment comment);

  void deleteAllByBlogId(Long id);

  Comment update(Long id, Comment comment);
}
