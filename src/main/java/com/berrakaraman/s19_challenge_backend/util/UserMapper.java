package com.berrakaraman.s19_challenge_backend.util;

import com.berrakaraman.s19_challenge_backend.dto.UserResponse;
import com.berrakaraman.s19_challenge_backend.entity.User;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getAbout(),
                user.getFollowing(),
                user.getFollowers(),
                user.getTweets(),
                user.getLikes(),
                user.getRetweets(),
                user.getAuthorities()
        );
    }
}

