package com.example.appduol.dao;

import com.example.appduol.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

  List<Comment> findAllByChecked(boolean checked);
}
