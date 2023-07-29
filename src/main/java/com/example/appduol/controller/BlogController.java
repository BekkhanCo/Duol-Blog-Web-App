package com.example.appduol.controller;

import com.example.appduol.model.entity.Blog;
import com.example.appduol.model.entity.Comment;
import com.example.appduol.service.BlogService;
import com.example.appduol.service.CommentService;
import com.example.appduol.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import java.time.LocalDateTime;
import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class BlogController {

  private final BlogService service;
  private final TopicService topicService;

  private final CommentService commentService;

  @GetMapping("/")
  public String displayAllBLogs(Model model) {
    Collection<Blog> blogs = service.getAll();
    model.addAttribute("blogs", blogs);
    return "index";
  }

  @GetMapping("/blog/{id}")
  public String getPost(@PathVariable Long id, Model model) {
    return service.getById(id).map(blog -> {
      model.addAttribute("blog", blog);
      model.addAttribute("checked_comments", commentService.getAllByFilter(true, blog.getId()));
      model.addAttribute("unchecked_comments", commentService.getAllByFilter(false, blog.getId()));
      model.addAttribute("comment", new Comment());
      return "blog";
    }).orElse("error");
  }

  @GetMapping("/blog/create")
  public String createNewBlog(Model model) {
    model.addAttribute("topics", topicService.getAll());
    model.addAttribute("blog", new Blog());
    return "blogForm";
  }

  @PostMapping("/blog/create")
  public String createNewBlog(@ModelAttribute Blog blog, BindingResult bindingResult, SessionStatus sessionStatus) {
    System.err.println("POST blog: " + blog); // for testing debugging purposes
    if (bindingResult.hasErrors()) {
      System.err.println("Blog did not validate");
      return "blogForm";
    }
    blog.setCreatedDate(LocalDateTime.now());
    blog.setChecked(false);
    blog.setCountOfReads(0);
    this.service.save(blog);
    System.err.println("SAVE blog: " + blog); // for testing debugging purposes
    sessionStatus.setComplete();
    return "redirect:/blog/%d".formatted(blog.getId());
  }

  @PostMapping("/blog/{id}/checked")
  public String setBlogChecked(@PathVariable("id") Long id) {
    service.update(id,Blog.builder().checked(true).build());
      return "redirect:/blog/%d".formatted(id);
  }

  @PostMapping("/blog/{id}/delete")
  @Transactional
  public String deleteBlog(@PathVariable("id") Long id) {
    return service.getById(id).map(blog -> {
      commentService.deleteAllByBlogId(id);
      service.delete(blog);
      return "redirect:/";
    }).orElse("error");
  }
}
