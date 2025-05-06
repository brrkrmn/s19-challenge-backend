package com.berrakaraman.s19_challenge_backend.controller;

import com.berrakaraman.s19_challenge_backend.dto.CreateTweetRequest;
import com.berrakaraman.s19_challenge_backend.dto.TweetResponse;
import com.berrakaraman.s19_challenge_backend.entity.Tweet;
import com.berrakaraman.s19_challenge_backend.service.TweetService;
import com.berrakaraman.s19_challenge_backend.util.TweetMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")
@AllArgsConstructor
public class TweetController {
    @Autowired
    private final TweetService tweetService;

    @GetMapping
    public List<TweetResponse> getAll() {
        return tweetService.getAll().stream().map(TweetMapper::toTweetResponse).toList();
    }

    @GetMapping("/{id}")
    public TweetResponse getById(@Positive @PathVariable("id") Long id) {
        return TweetMapper.toTweetResponse(tweetService.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TweetResponse create(@Validated @RequestBody CreateTweetRequest createTweetRequest) {
        return TweetMapper.toTweetResponse(tweetService.create(createTweetRequest.getContent()));
    }

    @PutMapping("/{id}")
    public Tweet replaceOrCreate(@Positive @PathVariable Long id,
                                 @Validated @RequestBody Tweet tweet) {
        return tweetService.replaceOrCreate(id, tweet);
    }

    @PatchMapping("/{id}")
    public Tweet update(@Positive @PathVariable Long id,
                        @Validated @RequestBody Tweet tweet) {
        return tweetService.update(id, tweet);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable("id") Long id) {
        tweetService.delete(id);
    }

    @PostMapping("/{id}/like")
    public void toggleLike(@Positive @PathVariable("id") Long id) {
        tweetService.toggleLike(id);
    }

    @PostMapping("/{id}/retweet")
    public void toggleRetweet(@Positive @PathVariable("id") Long id) {
        tweetService.toggleRetweet(id);
    }
}
