package com.berrakaraman.s19_challenge_backend.dto;

import com.berrakaraman.s19_challenge_backend.entity.Comment;
import com.berrakaraman.s19_challenge_backend.entity.Role;
import com.berrakaraman.s19_challenge_backend.entity.Tweet;
import com.berrakaraman.s19_challenge_backend.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public record UserResponse(Long id, String name, String username, String about, Set<User> following, Set<User> followers, List<Tweet> tweets, Set<Tweet> likes, Set<Tweet> retweets, Set<Comment> comments, Collection<? extends GrantedAuthority> authorities) {
}
