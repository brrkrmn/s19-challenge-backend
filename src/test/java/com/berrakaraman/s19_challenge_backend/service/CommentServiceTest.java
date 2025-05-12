package com.berrakaraman.s19_challenge_backend.service;

import com.berrakaraman.s19_challenge_backend.entity.Comment;
import com.berrakaraman.s19_challenge_backend.entity.Tweet;
import com.berrakaraman.s19_challenge_backend.entity.User;
import com.berrakaraman.s19_challenge_backend.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    private CommentService commentService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private TweetService tweetService;

    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl(commentRepository, authenticationService, tweetService);
    }

    @Test
    void getAll() {
        commentService.getAll();
        verify(commentRepository).findAll();
    }

    @Test
    void getById() {
        Comment comment = new Comment();
        comment.setId(9999L);
        comment.setContent("Comment");

        commentService.getById(9999L);
        verify(commentRepository).findById(9999L);
    }

    @Test
    void create() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setContent("tweet");
        tweet.setComments(new HashSet<>());

        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setComments(new HashSet<>());

        when(tweetService.create("tweet")).thenReturn(tweet);
        when(authenticationService.getAuthUser()).thenReturn(user);
        when(tweetService.getById(1L)).thenReturn(tweet);

        Comment comment = commentService.create(tweet.getId(), "test comment");
        verify(commentRepository).save(comment);
    }
}
