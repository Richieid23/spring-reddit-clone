package id.web.fitrarizki.spring_reddit_clone.mapper;

import id.web.fitrarizki.spring_reddit_clone.dto.CommentDto;
import id.web.fitrarizki.spring_reddit_clone.model.Comment;
import id.web.fitrarizki.spring_reddit_clone.model.Post;
import id.web.fitrarizki.spring_reddit_clone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentDto mapToDto(Comment comment);
}
