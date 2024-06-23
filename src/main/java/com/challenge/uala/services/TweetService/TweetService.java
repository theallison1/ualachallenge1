package com.challenge.uala.services.TweetService;

import com.challenge.uala.model.Tweet;

import java.util.List;

public interface TweetService {
    public Tweet postTweet(Tweet tweet) ;

    public List<Tweet> getTimeline(Long userId) ;
}
