package com.example.appduol.service;

import com.example.appduol.model.entity.Blog;
import com.example.appduol.model.entity.Topic;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public interface TopicService {

  Optional<Topic> getById(Integer id);

  Collection<Topic> getAll();
}
