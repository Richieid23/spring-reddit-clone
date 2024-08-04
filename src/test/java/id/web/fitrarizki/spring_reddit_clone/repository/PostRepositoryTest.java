package id.web.fitrarizki.spring_reddit_clone.repository;

import id.web.fitrarizki.spring_reddit_clone.BaseTest;
import id.web.fitrarizki.spring_reddit_clone.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest extends BaseTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    void shouldSavePost() {
        Post expectedPost = new Post(null, "First Post", "https://www.google.com", "Test", 0, null, Instant.now(), null);

        Post actualPost = postRepository.save(expectedPost);

        assertThat(actualPost).usingRecursiveComparison().ignoringFields("postId").isEqualTo(expectedPost);
    }
}
