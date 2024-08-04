package id.web.fitrarizki.spring_reddit_clone.service;

import id.web.fitrarizki.spring_reddit_clone.exception.SpringRedditException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public class CommentServiceTest {

    @Test
    @DisplayName("Test Should Pass When Comment do not Contains Swear Words")
    void shouldNotContainSwearWordsInsideComment() {
        CommentService commentService = new CommentService(null, null, null, null, null, null, null);
//        Assertions.assertFalse(commentService.containsSwearWords("This is a clean comment"));
        assertThat(commentService.containsSwearWords("This is a clean comment")).isFalse();
    }

    @Test
    @DisplayName("Should Throw Exception When Comment Contains Swear Words")
    void shouldFailWhenCommentContainsSwearWords() {
        CommentService commentService = new CommentService(null, null, null, null, null, null, null);
//        SpringRedditException exception = assertThrows(SpringRedditException.class, () -> commentService.containsSwearWords("This is shitty comment"));
//        assertTrue(exception.getMessage().contains("Comments contains unacceptable language"));
        assertThatThrownBy(() -> commentService.containsSwearWords("This is shitty comment"))
                .isInstanceOf(SpringRedditException.class)
                .hasMessage("Comments contains unacceptable language");
    }
}