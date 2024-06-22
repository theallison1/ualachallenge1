package com.challenge.uala.serviceUsuarios;

import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.User;

import java.util.List;

public interface TweetService {
    public Tweet postTweet(Tweet tweet) ;

    public List<Tweet> getTimeline(Long userId) ;
}
