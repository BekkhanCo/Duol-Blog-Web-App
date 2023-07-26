package com.example.appduol.service;

import com.example.appduol.model.entity.Blog;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface BlogService {

  Optional<Blog> getById(Long id);

  Collection<Blog> getAll();

  Blog save(Blog post);

  void delete(Blog post);
}
