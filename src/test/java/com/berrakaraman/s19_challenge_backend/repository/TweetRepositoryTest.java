package com.berrakaraman.s19_challenge_backend.repository;

import com.berrakaraman.s19_challenge_backend.entity.Tweet;
import com.berrakaraman.s19_challenge_backend.entity.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class TweetRepositoryTest {
    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Tweet testTweet;

    @BeforeEach
    void setUp() {
        testUser = userRepository.findByUsername("testUser").orElse(null);

        if (testUser == null) {
            User user = new User();
            user.setName("testUser");
            user.setUsername("testUser");
            user.setEmail("testUser@testUser.com");
            user.setPassword("testUser");
            testUser = userRepository.save(user);
        }

        testTweet = new Tweet();
        testTweet.setContent("test tweet");
        testTweet.setUser(testUser);
        testTweet = tweetRepository.save(testTweet);

        testUser.addLike(testTweet);
        testUser.addRetweet(testTweet);
        userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        if (testUser != null && testTweet != null) {
            testUser.removeLike(testTweet);
            testUser.removeRetweet(testTweet);
            userRepository.save(testUser);
        }

        if (testTweet != null) tweetRepository.delete(testTweet);
        if (testUser != null) userRepository.delete(testUser);
    }

    @DisplayName("Can get users who liked the tweet from tweet id")
    @Test
    void getUsersWhoLiked() {
        Set<User> users = tweetRepository.getUsersWhoLiked(testTweet.getId());
        assertEquals(1, users.size());
        assertTrue(users.contains(testUser));
    }

    @DisplayName("Can get users who retweeted the tweet from tweet id")
    @Test
    void getUsersWhoRetweeted() {
        Set<User> users = tweetRepository.getUsersWhoRetweeted(testTweet.getId());
        assertEquals(1, users.size());
        assertTrue(users.contains(testUser));
    }
}
