package com.berrakaraman.s19_challenge_backend.util;

import com.berrakaraman.s19_challenge_backend.dto.TweetResponse;
import com.berrakaraman.s19_challenge_backend.entity.Tweet;
import com.berrakaraman.s19_challenge_backend.entity.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TweetMapper {
    public static TweetResponse toTweetResponse(Tweet tweet) {
        return new TweetResponse(
                tweet.getId(),
                tweet.getContent(),
                UserMapper.toUserPreview(tweet.getUser()),
                UserMapper.toUserPreviewSet(tweet.getLikedBy()),
                tweet.getComments(),
                UserMapper.toUserPreviewSet(tweet.getRetweetedBy())
        );
    }

    public static Set<TweetResponse> toTweetResponseSet(List<Tweet> tweets) {
        return tweets.stream().map(TweetMapper::toTweetResponse).collect(Collectors.toSet());
    }

    public static List<TweetResponse> toTweetResponseList(List<Tweet> tweets) {
        return tweets.stream().map(TweetMapper::toTweetResponse).toList();
    }
}
