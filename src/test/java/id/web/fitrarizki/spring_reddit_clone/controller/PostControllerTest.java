package id.web.fitrarizki.spring_reddit_clone.controller;

import id.web.fitrarizki.spring_reddit_clone.dto.PostResponse;
import id.web.fitrarizki.spring_reddit_clone.security.JwtProvider;
import id.web.fitrarizki.spring_reddit_clone.service.PostService;
import id.web.fitrarizki.spring_reddit_clone.service.UserDetailServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Arrays.asList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest {

    @MockBean
    private PostService postService;
    @MockBean
    private UserDetailServiceImpl userDetailService;
    @MockBean
    private JwtProvider jwtProvider;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @DisplayName("Should List All Posts When Making GET request to endpoint - /api/posts")
    @WithMockUser(username = "fitrarizki")
    public void shouldListAllPost() throws Exception {
        PostResponse postRequest1 = new PostResponse(1L, "Post Name", "http://url.site", "Description", "User 1",
                "Subreddit Name", 0, 0, "1 day ago");
        PostResponse postRequest2 = new PostResponse(2L, "Post Name 2", "http://url2.site2", "Description2", "User 2",
                "Subreddit Name 2", 0, 0, "2 days ago");

        Mockito.when(postService.getAllPosts()).thenReturn(asList(postRequest1, postRequest2));

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.size()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].postName", Matchers.is("Post Name")))
                .andExpect(jsonPath("$[0].url", Matchers.is("http://url.site")))
                .andExpect(jsonPath("$[1].url", Matchers.is("http://url2.site2")))
                .andExpect(jsonPath("$[1].postName", Matchers.is("Post Name 2")))
                .andExpect(jsonPath("$[1].id", Matchers.is(2)));
    }
}
