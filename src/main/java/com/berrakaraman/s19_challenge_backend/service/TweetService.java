package com.berrakaraman.s19_challenge_backend.service;

import com.berrakaraman.s19_challenge_backend.entity.Tweet;

import java.util.List;

public interface TweetService {
    List<Tweet> getAll();
    Tweet getById(Long id);
    Tweet create(String content);
    Tweet replaceOrCreate(Long id, String content);
    Tweet update(Long id, String content);
    void delete(Long id);
    Tweet toggleRetweet(Long id);
    Tweet toggleLike(Long id);
}
