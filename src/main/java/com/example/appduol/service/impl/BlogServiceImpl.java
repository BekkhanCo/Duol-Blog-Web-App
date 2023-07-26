package com.example.appduol.service.impl;

import com.example.appduol.dao.BlogRepository;
import com.example.appduol.model.entity.Blog;
import com.example.appduol.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

  private final BlogRepository repository;

  @Override
  public Optional<Blog> getById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Collection<Blog> getAll() {
    return repository.findAll();
  }

  @Override
  public Blog save(Blog post) {
    return repository.saveAndFlush(post);
  }

  @Override
  public void delete(Blog post) {
    repository.delete(post);
  }
}
