package com.example.appduol.service.impl;

import com.example.appduol.dao.CommentRepository;
import com.example.appduol.exception.DataNotFoundException;
import com.example.appduol.model.entity.Comment;
import com.example.appduol.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository repository;
  private final ModelMapper modelMapper;

  @Override
  public Optional<Comment> getById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Collection<Comment> getAll() {
    return repository.findAll();
  }

  @Override
  public Collection<Comment> getAllByFilter(Boolean checked) {
    return Optional.of(checked)
        .map(repository::findAllByChecked)
        .orElse(repository.findAll());
  }

  @Override
  public Comment save(Comment comment) {
    comment.setCreatedDate(LocalDateTime.now());
    comment.setCountOfUnuseful(0);
    comment.setCountOfUseful(0);
    comment.setChecked(false);
    return repository.saveAndFlush(comment);
  }

  @Override
  public void delete(Comment comment) {
    repository.delete(comment);
  }

  @Override
  public Comment update(Long id, Comment comment) {
    Comment oldComment = repository.findById(id)
        .orElseThrow(() -> new DataNotFoundException("Comment with id: %d not found".formatted(id)));
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    modelMapper.map(comment,oldComment);
    return oldComment;
  }
}
