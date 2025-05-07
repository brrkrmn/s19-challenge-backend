package com.berrakaraman.s19_challenge_backend.dto;

import java.util.Set;

public record TweetResponse(
    Long id,
    String content,
    UserPreview user,
    Set<UserPreview> likedBy,
    Set<CommentResponse> comments,
    Set<UserPreview> retweetedBy
) {}
