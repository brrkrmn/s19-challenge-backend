package com.berrakaraman.s19_challenge_backend.service;

import com.berrakaraman.s19_challenge_backend.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAll();
    Comment getById(Long id);
    Comment create(Long tweetId, Comment comment);
    Comment update(Long id, Comment comment);
    void deleteComment(Long id);
}
