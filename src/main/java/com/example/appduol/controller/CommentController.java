package com.example.appduol.controller;

import com.example.appduol.model.entity.Blog;
import com.example.appduol.model.entity.Comment;
import com.example.appduol.service.BlogService;
import com.example.appduol.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class CommentController {

  private final CommentService service;
  private final BlogService blogService;

  @GetMapping("/blog/{id}/comment/create")
  public String createNewBlog(@PathVariable("id") Long blogId, Model model) {
    model.addAttribute("unchecked_comments", service.getAllByFilter(false, blogId));
    model.addAttribute("blogId", blogId);
    return "comment";
  }

  @PostMapping("blog/{id}/comment/create")
  public String createNewComment(
      @PathVariable("id") Long blogId,
      @RequestParam(value = "comment",required = false) String content) {
    return blogService.getById(blogId).map(blog -> {
      Comment comment = Comment.builder()
//          .createdDate(LocalDateTime.now())
          .countOfUnuseful(0)
          .countOfUseful(0)
          .checked(false)
          .text(content)
          .blog(blog)
          .build();
      this.service.save(comment);
      return "redirect:/blog/%d/comment/create".formatted(blogId);
    }).orElse("error");
  }

  @PostMapping("/comment/{id}/checked")
  public String setCommentChecked(@PathVariable("id") Long id) {
    return service.getById(id).map(comment -> {
      comment.setChecked(true);
      service.save(comment);
      return "redirect:/blog/%d".formatted(comment.getBlog().getId());
    }).orElse("error");
  }

  @PostMapping("/comment/{id}/delete")
  public String deleteComment(@PathVariable("id") Long id) {
    return service.getById(id).map(comment -> {
      service.delete(comment);
      return "redirect:/blog/%d".formatted(comment.getBlog().getId());
    }).orElse("error");
  }
}
