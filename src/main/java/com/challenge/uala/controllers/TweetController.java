package com.challenge.uala.controllers;


import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.TweetDto.TweetDTO;
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
    public Tweet postTweet(@RequestBody TweetDTO tweetDTO) {
        return tweetService.postTweet(tweetDTO  );
    }

    @GetMapping("/timeline/{userId}")
    public List<Tweet> getTimeline(@PathVariable Long userId) {
        return tweetService.getTimeline(userId);
    }

}

