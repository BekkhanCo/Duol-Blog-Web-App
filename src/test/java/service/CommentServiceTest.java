package service;

import com.example.appduol.dao.BlogRepository;
import com.example.appduol.dao.TopicRepository;
import com.example.appduol.model.entity.Blog;
import com.example.appduol.model.entity.Comment;
import com.example.appduol.model.entity.Topic;
import com.example.appduol.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentServiceTest {

  @Autowired
  private CommentService commentService;
  @Autowired
  private BlogRepository blogRepository;
  @Autowired
  private TopicRepository topicRepository;

  @Test
  public void givenComment_whenSave_thenCommentIsSaved() {
    // given
    Blog blog = insertBlog();
    Comment comment = Comment.builder()
        .blog(blog)
        .text("Comment body")
        .build();
    // when
    Comment actual = commentService.save(comment);
    // then
    assertThat(actual.getCreatedDate()).isNotNull();
    assertThat(actual).isNotNull();
    assertThat(actual.getId()).isNotNull();
    assertThat(actual.getBlog()).isNotNull();
    assertThat(commentService.getById(actual.getId())).isNotEmpty();
    commentService.delete(actual);
  }

  @Test
  public void givenCommentId_whenNotExist_thenEmptyOptionalReturned() {
    // given
    Long id = 0L;
    // then
    assertThat(commentService.getById(id)).isEmpty();
  }

  @Test
  public void testFilterParams_thenDifferentListOfObjectsReturn() {
    // given
    Comment comment = insert();
    // then
    assertThat(commentService.getAllByFilter(comment.getChecked(), comment.getBlog().getId())).hasSize(1);
    assertThat(commentService.getAllByFilter(!comment.getChecked(), comment.getBlog().getId())).isNotNull().isEmpty();
    assertThat(commentService.getAllByFilter(null, comment.getBlog().getId())).isNotNull();
    assertThat(commentService.getAllByFilter(null, null)).isNotNull().hasSize(commentService.getAll().size());
    commentService.delete(comment);
  }

  @Test
  public void testCanGetListOfComments() {
    // given
    Comment comment = insert();
    // then
    assertThat(commentService.getAll()).isNotNull().isNotEmpty().isInstanceOf(List.class);
    commentService.delete(comment);
  }

  @Test
  public void testDeleteComment(){
    // given
    Comment comment = insert();
    Long id = comment.getId();
    assertThat(commentService.getById(id)).isNotEmpty();
    // when
    commentService.delete(comment);
    // then
    assertThat(commentService.getById(id)).isEmpty();
  }

  private Comment insert() {
    Blog blog = insertBlog();
    return commentService.save(Comment.builder()
            .blog(blog)
            .checked(false)
            .text("Hello world")
            .build());
  }

  private Blog insertBlog() {
    return blogRepository.save(Blog.builder()
        .title("Cool Title")
        .content("Cool Content")
        .topic(topicRepository.save(Topic.builder().name("Test").build()))
        .build());
  }
}
