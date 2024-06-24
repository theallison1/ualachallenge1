package com.challenge.uala.services.TweetService;

import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.TweetDto.TweetDTO;

import java.util.List;

public interface TweetService {
    public Tweet postTweet(TweetDTO tweet) ;

    public List<Tweet> getTimeline(Long userId) ;

    List<Tweet> getTimelineForUser(Long userId);
}
