package com.berrakaraman.s19_challenge_backend.controller;

import com.berrakaraman.s19_challenge_backend.dto.TweetRequest;
import com.berrakaraman.s19_challenge_backend.dto.TweetResponse;
import com.berrakaraman.s19_challenge_backend.service.TweetService;
import com.berrakaraman.s19_challenge_backend.util.TweetMapper;
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
    public TweetResponse create(@Validated @RequestBody TweetRequest tweetRequest) {
        return TweetMapper.toTweetResponse(tweetService.create(tweetRequest.getContent()));
    }

    @PutMapping("/{id}")
    public TweetResponse replaceOrCreate(@Positive @PathVariable Long id,
                                 @Validated @RequestBody TweetRequest tweetRequest) {
        return TweetMapper.toTweetResponse(tweetService.replaceOrCreate(id, tweetRequest.getContent()));
    }

    @PatchMapping("/{id}")
    public TweetResponse update(@Positive @PathVariable Long id,
                        @Validated @RequestBody TweetRequest tweetRequest) {
        return TweetMapper.toTweetResponse(tweetService.update(id, tweetRequest.getContent()));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive @PathVariable("id") Long id) {
        tweetService.delete(id);
    }

    @PostMapping("/{id}/like")
    public TweetResponse toggleLike(@Positive @PathVariable("id") Long id) {
        return TweetMapper.toTweetResponse(tweetService.toggleLike(id));
    }

    @PostMapping("/{id}/retweet")
    public TweetResponse toggleRetweet(@Positive @PathVariable("id") Long id) {
        return TweetMapper.toTweetResponse(tweetService.toggleRetweet(id));
    }
}
