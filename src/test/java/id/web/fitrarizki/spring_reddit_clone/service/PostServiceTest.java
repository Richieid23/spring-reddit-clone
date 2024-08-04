package id.web.fitrarizki.spring_reddit_clone.service;

import id.web.fitrarizki.spring_reddit_clone.dto.PostRequest;
import id.web.fitrarizki.spring_reddit_clone.dto.PostResponse;
import id.web.fitrarizki.spring_reddit_clone.mapper.PostMapper;
import id.web.fitrarizki.spring_reddit_clone.model.Post;
import id.web.fitrarizki.spring_reddit_clone.model.Subreddit;
import id.web.fitrarizki.spring_reddit_clone.model.User;
import id.web.fitrarizki.spring_reddit_clone.repository.PostRepository;
import id.web.fitrarizki.spring_reddit_clone.repository.SubredditRepository;
import id.web.fitrarizki.spring_reddit_clone.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private SubredditRepository subredditRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthService authService;
    @Mock
    private PostMapper postMapper;
    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    PostService postService;

    @BeforeEach
    void setup() {
        postService = new PostService(postRepository, subredditRepository, userRepository, authService, postMapper);
    }

    @Test
    @DisplayName("Should Find Post By Id")
    void shouldFindPostById() {
        Post post = new Post(123L, "First", "http://google.com", "Test", 0, null, Instant.now(), null);
        PostResponse expectedPostResponse = new PostResponse(123L, "First", "http://google.com", "Test", "Test User", "Test Subreddit", 0, 0, "1 Hour ago");

        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
        Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(expectedPostResponse);

        PostResponse actualPostResponse = postService.getPost(123L);

        assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
        assertThat(actualPostResponse.getPostName()).isEqualTo(expectedPostResponse.getPostName());
    }

    @Test
    @DisplayName("Should Save Post")
    void shouldSavePost() {
        User currentUser = new User(123L, "fitrarizki", "password", "fitrarizki.dev@gmail.com", Instant.now(), true);
        Subreddit subreddit = new Subreddit(123L, "First Subreddit", "Subreddit description", emptyList(), Instant.now(), currentUser);
        Post post = new Post(123L, "First", "http://google.com", "Test", 0, null, Instant.now(), null);
        PostRequest postRequest = new PostRequest(null, "First Subreddit", "First", "http://google.com", "Test");

        Mockito.when(subredditRepository.findByName("First Subreddit")).thenReturn(Optional.of(subreddit));
        Mockito.when(postMapper.map(postRequest, subreddit, currentUser)).thenReturn(post);
        Mockito.when(authService.getCurrentUser()).thenReturn(currentUser);

        postService.save(postRequest);

        Mockito.verify(postRepository, Mockito.times(1)).save(postArgumentCaptor.capture());

        assertThat(postArgumentCaptor.getValue().getPostId()).isEqualTo(123L);
        assertThat(postArgumentCaptor.getValue().getPostName()).isEqualTo("First");
    }
}