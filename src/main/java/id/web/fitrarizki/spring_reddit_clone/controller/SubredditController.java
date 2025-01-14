package id.web.fitrarizki.spring_reddit_clone.controller;

import id.web.fitrarizki.spring_reddit_clone.dto.SubredditDto;
import id.web.fitrarizki.spring_reddit_clone.service.SubredditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
@Slf4j
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<?> createSubreddit(@RequestBody SubredditDto subredditDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.save(subredditDto));
    }

    @GetMapping
    public ResponseEntity<?> getAllSubreddit() {
        return ResponseEntity.ok(subredditService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubredditById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(subredditService.getSubredditById(id));
    }
}
