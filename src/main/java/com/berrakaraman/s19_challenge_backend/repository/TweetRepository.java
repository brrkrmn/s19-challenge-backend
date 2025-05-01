package com.berrakaraman.s19_challenge_backend.repository;

import com.berrakaraman.s19_challenge_backend.entity.Tweet;
import com.berrakaraman.s19_challenge_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    @Query(value = "SELECT * FROM app_user WHERE id IN (SELECT user_id FROM tweet_likes WHERE tweet_id = :id)", nativeQuery = true)
    Set<User> getUsersWhoLikedTweet(Long id);
}
