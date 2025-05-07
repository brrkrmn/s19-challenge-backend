package com.berrakaraman.s19_challenge_backend.repository;

import com.berrakaraman.s19_challenge_backend.entity.Tweet;
import com.berrakaraman.s19_challenge_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
    @Query(value = "SELECT * FROM app_user WHERE id IN (SELECT user_id FROM tweet_likes WHERE tweet_id = :id)", nativeQuery = true)
    Set<User> getUsersWhoLiked(@Param("id") Long id);

    @Query(value = "SELECT * FROM app_user WHERE id IN (SELECT user_id FROM user_retweets WHERE tweet_id = :id)", nativeQuery = true)
    Set<User> getUsersWhoRetweeted(@Param("id") Long id);
}
