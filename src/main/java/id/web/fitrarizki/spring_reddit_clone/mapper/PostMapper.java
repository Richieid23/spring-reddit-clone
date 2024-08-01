package id.web.fitrarizki.spring_reddit_clone.mapper;

import id.web.fitrarizki.spring_reddit_clone.dto.PostRequest;
import id.web.fitrarizki.spring_reddit_clone.dto.PostResponse;
import id.web.fitrarizki.spring_reddit_clone.model.Post;
import id.web.fitrarizki.spring_reddit_clone.model.Subreddit;
import id.web.fitrarizki.spring_reddit_clone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapToDto(Post post);
}
