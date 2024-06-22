package com.challenge.uala.serviceUsuarios;

import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.Usuarios;

import java.util.List;

public interface TweetService {
    Tweet saveTweet(Tweet tweet);
    List<Tweet> findByUser(Usuarios user);
}
