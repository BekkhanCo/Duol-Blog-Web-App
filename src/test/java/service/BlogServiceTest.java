package service;

import com.example.appduol.model.entity.Blog;
import com.example.appduol.model.entity.Topic;
import com.example.appduol.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogServiceTest {

  @Autowired
  private BlogService blogService;

  @Test
  public void givenBlog_whenSave_thenBlogIsSaved() {
    // given
    Blog blog = initBlog();
    // when
    Blog actual = blogService.save(blog);
    // then
    assertThat(actual.getCreatedDate()).isNotNull();
    assertThat(actual.getId()).isNotNull();
    assertThat(blogService.getById(actual.getId())).isNotEmpty();
    blogService.delete(blog);
  }

  @Test
  public void givenBlogId_whenNotExist_thenEmptyOptionalReturned() {
    // given
    Long id = 0L;
    // then
    assertThat(blogService.getById(id)).isEmpty();
  }

  @Test
  public void testCanGetListOfBlogs() {
    // given
    Blog blog = insert();
    // then
    assertThat(blogService.getAll()).isNotNull().isNotEmpty().isInstanceOf(List.class);
    blogService.delete(blog);
  }



  @Test
  public void givenBlog_whenOnly2FiledsChanged_thenBlogUpdated() {
    // given
    Blog blog = insert();
    // when
    Blog updated = blogService.update(blog.getId(), Blog.builder()
        .checked(true)
        .countOfReads(10)
        .build());
    // then
    assertThat(updated).isNotNull();
    assertThat(blog.getId()).isEqualTo(updated.getId());
    assertThat(updated.getCountOfReads()).isEqualTo(10);
    assertThat(updated.getChecked()).isTrue();
  }

  @Test
  public void testDeleteBlog(){
    // given
    Blog blog = insert();
    Long id = blog.getId();
    assertThat(blogService.getById(id)).isNotEmpty();
    // when
    blogService.delete(blog);
    // then
    assertThat(blogService.getById(id)).isEmpty();
  }

  private Blog initBlog() {
    return Blog.builder()
        .content("Test content")
        .checked(false)
        .title("Test title")
        .topic(initTopc())
        .build();
  }

  private Topic initTopc() {
    return new Topic(1, "Test");
  }

  private Blog insert() {
    return blogService.save(initBlog());
  }
}
