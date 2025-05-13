package com.berrakaraman.s19_challenge_backend.util;

import com.berrakaraman.s19_challenge_backend.dto.UserPreview;
import com.berrakaraman.s19_challenge_backend.dto.UserResponse;
import com.berrakaraman.s19_challenge_backend.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getAbout(),
                toUserPreviewSet(user.getFollowing()),
                toUserPreviewSet(user.getFollowers()),
                TweetMapper.toTweetResponseList(user.getTweets()),
                TweetMapper.toTweetResponseSet(user.getLikes().stream().toList()),
                TweetMapper.toTweetResponseSet(user.getRetweets().stream().toList()),
                CommentMapper.toCommentResponseSet(user.getComments()),
                user.getAuthorities()
        );
    }

    public static UserPreview toUserPreview(User user) {
        return new UserPreview(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getAbout()
        );
    }

    public static Set<UserPreview> toUserPreviewSet(Set<User> users) {
        return users.stream().map(UserMapper::toUserPreview).collect(Collectors.toSet());
    }
}

