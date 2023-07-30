package service;

import com.example.appduol.dao.TopicRepository;
import com.example.appduol.model.entity.Topic;
import com.example.appduol.service.TopicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TopicServiceTest {

  @Autowired
  private TopicService topicService;

  @Autowired
  private TopicRepository topicRepository;

  @Test
  public void testCanGetTopicById() {
    // given
    Topic topic = insert();
    Integer id = topic.getId();
    // then
    assertThat(topicService.getById(id)).isNotEmpty();
    destroy(topic);
  }

  @Test
  public void testCanGetNotNullListOfTopics() {
    // given
    Topic topic = insert();
    // then
    assertThat(topicService.getAll()).isNotEmpty().isInstanceOf(List.class);
    destroy(topic);
  }

  private Topic insert() {
    return topicRepository.save(Topic.builder()
        .name("Design")
        .build());
  }

  private void destroy(Topic topic) {
    topicRepository.delete(topic);
  }
}