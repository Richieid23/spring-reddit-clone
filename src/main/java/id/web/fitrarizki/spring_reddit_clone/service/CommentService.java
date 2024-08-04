package id.web.fitrarizki.spring_reddit_clone.service;

import id.web.fitrarizki.spring_reddit_clone.dto.CommentDto;
import id.web.fitrarizki.spring_reddit_clone.exception.PostNotFoundException;
import id.web.fitrarizki.spring_reddit_clone.exception.SpringRedditException;
import id.web.fitrarizki.spring_reddit_clone.mapper.CommentMapper;
import id.web.fitrarizki.spring_reddit_clone.model.Comment;
import id.web.fitrarizki.spring_reddit_clone.model.NotificationEmail;
import id.web.fitrarizki.spring_reddit_clone.model.Post;
import id.web.fitrarizki.spring_reddit_clone.model.User;
import id.web.fitrarizki.spring_reddit_clone.repository.CommentRepository;
import id.web.fitrarizki.spring_reddit_clone.repository.PostRepository;
import id.web.fitrarizki.spring_reddit_clone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(authService.getCurrentUser().getUsername() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CommentDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public boolean containsSwearWords(String comment) {
        if (comment.contains("shit")) {
            throw new SpringRedditException("Comments contains unacceptable language");
        }
        return false;
    }
}