package com.challenge.uala.controllers;


import com.challenge.uala.model.Tweet;
import com.challenge.uala.services.TweetService.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tweets")

public class TweetController {
    @Autowired
    private TweetService tweetService;

    @PostMapping
    public Tweet postTweet(@RequestBody Tweet tweet) {
        return tweetService.postTweet(tweet);
    }

    @GetMapping("/timeline/{userId}")
    public List<Tweet> getTimeline(@PathVariable Long userId) {
        return tweetService.getTimeline(userId);
    }

}

