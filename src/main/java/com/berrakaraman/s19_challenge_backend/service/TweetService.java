package com.berrakaraman.s19_challenge_backend.service;

import com.berrakaraman.s19_challenge_backend.entity.Tweet;

import java.util.List;

public interface TweetService {
    List<Tweet> findAll();
    Tweet findById(Long id);
}
