package com.example.appduol.dao;

import com.example.appduol.model.entity.Blog;
import com.example.appduol.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long>, JpaSpecificationExecutor<Comment> {
  void deleteAllByBlog(Blog blog);
}
