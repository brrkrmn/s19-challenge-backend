package com.berrakaraman.s19_challenge_backend.service;

import com.berrakaraman.s19_challenge_backend.entity.Tweet;

import java.util.List;

public interface TweetService {
    List<Tweet> getAll();
    Tweet getById(Long id);
    Tweet create(String content);
    Tweet replaceOrCreate(Long id, Tweet tweet);
    Tweet update(Long id, Tweet tweet);
    void delete(Long id);
}
