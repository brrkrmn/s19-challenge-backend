package com.berrakaraman.s19_challenge_backend.repository;

import com.berrakaraman.s19_challenge_backend.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {

}
