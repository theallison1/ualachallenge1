package com.challenge.uala.serviceUsuarios;

import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.User;
import com.challenge.uala.repoUsuarios.TweetRepository;
import com.challenge.uala.repoUsuarios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetServiceImpl implements TweetService{

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    public Tweet postTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    public List<Tweet> getTimeline(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        return tweetRepository.findByUserInOrderByCreatedAtDesc(List.copyOf(user.getFollowers()));
    }
}
