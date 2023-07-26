package com.example.appduol.service.impl;

import com.example.appduol.dao.TopicRepository;
import com.example.appduol.model.entity.Topic;
import com.example.appduol.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

  private final TopicRepository repository;

  @Override
  public Optional<Topic> getById(Integer id) {
    return repository.findById(id);
  }

  @Override
  public Collection<Topic> getAll() {
    return repository.findAll();
  }
}
