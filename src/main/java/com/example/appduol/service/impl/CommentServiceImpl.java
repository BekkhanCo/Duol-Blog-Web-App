package com.example.appduol.service.impl;

import com.example.appduol.dao.CommentRepository;
import com.example.appduol.exception.DataNotFoundException;
import com.example.appduol.model.entity.Blog;
import com.example.appduol.model.entity.Comment;
import com.example.appduol.service.CommentService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepository repository;
  private final ModelMapper modelMapper;

  @Override
  @Transactional
  public Optional<Comment> getById(Long id) {
    return repository.findById(id);
  }

  @Override
  public Collection<Comment> getAll() {
    return repository.findAll();
  }

  @Override
  public Collection<Comment> getAllByFilter(Boolean checked, Long blogId) {
    return repository.findAll(getFilteringSpecification(checked, blogId));
  }

  @Override
  public Comment save(Comment comment) {
    return repository.saveAndFlush(comment);
  }

  @Override
  public void delete(Comment comment) {
    repository.delete(comment);
  }

  @Override
  public void deleteAllByBlogId(Long id) {
    repository.deleteAllByBlog(Blog.builder().id(id).build());
  }

  @Override
  public Comment update(Long id, Comment comment) {
    Comment oldComment = repository.findById(id)
        .orElseThrow(() -> new DataNotFoundException("Comment with id: %d not found".formatted(id)));
    modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
    modelMapper.map(comment, oldComment);
    return oldComment;
  }

  private Specification<Comment> getFilteringSpecification(Boolean checked, Long blogId) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new LinkedList<>();
      query.orderBy(criteriaBuilder.desc(root.get("id")));
      if (checked != null) {
        predicates.add(criteriaBuilder.equal(root.get("checked"), checked));
      }
      if (blogId != null) {
        predicates.add(criteriaBuilder.equal(root.get("blog"), Blog.builder().id(blogId).build()));
      }
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
