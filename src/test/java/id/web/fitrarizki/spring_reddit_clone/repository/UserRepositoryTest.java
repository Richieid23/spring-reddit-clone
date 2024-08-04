package id.web.fitrarizki.spring_reddit_clone.repository;

import id.web.fitrarizki.spring_reddit_clone.BaseTest;
import id.web.fitrarizki.spring_reddit_clone.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest extends BaseTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldSaveUser() {
        User expectedUser = new User(123L, "fitrarizki", "secret", "fitrarizki.dev@gmail.com", Instant.now(), true);
        User actualUser = userRepository.save(expectedUser);

        assertThat(actualUser).usingRecursiveComparison().ignoringFields("userId").isEqualTo(expectedUser);
    }
}
