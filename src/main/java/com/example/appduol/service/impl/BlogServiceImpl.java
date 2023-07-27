package com.example.appduol.service.impl;

import com.example.appduol.dao.BlogRepository;
import com.example.appduol.exception.DataNotFoundException;
import com.example.appduol.model.entity.Blog;
import com.example.appduol.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

  private final BlogRepository repository;
  private final ModelMapper modelMapper;

  @Override
  public Optional<Blog> getById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Collection<Blog> getAll() {
    return repository.findAll();
  }

  @Override
  public Blog save(Blog blog) {
    blog.setCreatedDate(LocalDateTime.now());
    blog.setCountOfReads(0);
    blog.setChecked(false);
    return repository.saveAndFlush(blog);
  }

  @Override
  public void delete(Blog post) {
    repository.delete(post);
  }

  @Override
  public Blog update(Long id, Blog blog) {
    Blog oldblog = repository.findById(id).orElseThrow(() -> new DataNotFoundException("Blog with id: %d not found".formatted(id)));
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    modelMapper.map(blog, oldblog);
    return repository.save(oldblog);
  }
}
