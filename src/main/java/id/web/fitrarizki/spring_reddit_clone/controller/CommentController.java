package id.web.fitrarizki.spring_reddit_clone.controller;

import id.web.fitrarizki.spring_reddit_clone.dto.CommentDto;
import id.web.fitrarizki.spring_reddit_clone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentDto commentsDto) {
        commentService.save(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/by-post/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForPost(@PathVariable Long postId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsForPost(postId));
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentDto>> getAllCommentsForUser(@PathVariable String userName){
        return ResponseEntity.status(HttpStatus.OK)
                .body(commentService.getAllCommentsForUser(userName));
    }
}
