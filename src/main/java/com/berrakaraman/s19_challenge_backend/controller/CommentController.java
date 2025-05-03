package com.berrakaraman.s19_challenge_backend.controller;

import com.berrakaraman.s19_challenge_backend.entity.Comment;
import com.berrakaraman.s19_challenge_backend.service.CommentService;
import com.berrakaraman.s19_challenge_backend.service.TweetService;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentController {
    @Autowired
    private final CommentService commentService;

    @GetMapping
    public List<Comment> getAll() {
        return commentService.getAll();
    }

    @GetMapping("/{id}")
    public Comment getById(@Positive @PathVariable Long id) {
        return commentService.getById(id);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@Positive @PathVariable Long tweetId,
                          @Validated @RequestBody Comment comment) {
        return commentService.create(tweetId, comment);
    }

    @PutMapping("/{id}")
    public Comment update(@Positive @PathVariable Long id,
                          @Validated @RequestBody Comment comment) {
        return commentService.update(id, comment);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
