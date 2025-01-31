package id.web.fitrarizki.spring_reddit_clone.repository;

import id.web.fitrarizki.spring_reddit_clone.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTestEmbedded {
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUser() {
        User user = new User(null, "fitrarizki", "password", "fitrarizki.dev@gmail.com", Instant.now(), true);
        User savedUser = userRepository.save(user);

        assertThat(savedUser).usingRecursiveComparison().ignoringFields("userId").isEqualTo(user);
    }

    @Test
    @Sql("classpath:test-data.sql")
    void shouldSaveUserThroughSqlFile() {
        Optional<User> user = userRepository.findByUsername("testuser_sql");
        assertThat(user).isNotEmpty();
    }
}
