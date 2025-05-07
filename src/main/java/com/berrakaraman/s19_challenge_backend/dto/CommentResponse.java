package com.berrakaraman.s19_challenge_backend.dto;

public record CommentResponse(Long id, String content, Long tweetId, Long userId) {
}
