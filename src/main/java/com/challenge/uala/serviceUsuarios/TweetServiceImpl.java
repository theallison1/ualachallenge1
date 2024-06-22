package com.challenge.uala.serviceUsuarios;

import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.Usuarios;
import com.challenge.uala.repoUsuarios.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService{
    @Autowired
    private TweetRepository tweetRepository;

    @Override
    public Tweet saveTweet(Tweet tweet) {
        tweet.setCreatedAt(LocalDateTime.now());
        return tweetRepository.save(tweet);
    }

    @Override
    public List<Tweet> findByUser(Usuarios user) {
        return tweetRepository.findByUserOrderByCreatedAtDesc(user);
    }
}
