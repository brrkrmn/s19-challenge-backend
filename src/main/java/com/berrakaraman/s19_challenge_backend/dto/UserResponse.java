package com.berrakaraman.s19_challenge_backend.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public record UserResponse(
        Long id,
        String name,
        String username,
        String password,
        String about,
        Set<UserPreview> following,
        Set<UserPreview> followers,
        List<TweetResponse> tweets,
        Set<TweetResponse> likes,
        Set<TweetResponse> retweets,
        Set<CommentResponse> comments,
        Collection<? extends GrantedAuthority> authorities
) {}
