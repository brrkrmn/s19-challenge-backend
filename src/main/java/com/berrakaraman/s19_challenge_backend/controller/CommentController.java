package com.berrakaraman.s19_challenge_backend.controller;

import com.berrakaraman.s19_challenge_backend.dto.CommentRequest;
import com.berrakaraman.s19_challenge_backend.dto.CommentResponse;
import com.berrakaraman.s19_challenge_backend.service.CommentService;
import com.berrakaraman.s19_challenge_backend.util.CommentMapper;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {
    @Autowired
    private final CommentService commentService;

    @GetMapping
    public List<CommentResponse> getAll() {
        return CommentMapper.toCommentResponseList(commentService.getAll());
    }

    @GetMapping("/{id}")
    public CommentResponse getById(@Positive @PathVariable("id") Long id) {
        return CommentMapper.toCommentResponse(commentService.getById(id));
    }

    @PostMapping("/{tweetId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponse create(@Positive @PathVariable("tweetId") Long tweetId,
                          @Validated @RequestBody CommentRequest commentRequest) {
        return CommentMapper.toCommentResponse(commentService.create(tweetId, commentRequest.getContent()));
    }

    @PutMapping("/{id}")
    public CommentResponse update(@Positive @PathVariable("id") Long id,
                          @Validated @RequestBody CommentRequest commentRequest) {
        return CommentMapper.toCommentResponse(commentService.update(id, commentRequest.getContent()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable Long id) {
        commentService.deleteComment(id);
    }
}
