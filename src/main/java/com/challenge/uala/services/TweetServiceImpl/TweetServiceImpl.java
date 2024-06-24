package com.challenge.uala.services.TweetServiceImpl;

import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.TweetDto.TweetDTO;
import com.challenge.uala.model.User;
import com.challenge.uala.repos.TweetRepository;
import com.challenge.uala.repos.UserRepository;
import com.challenge.uala.services.TweetService.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    public Tweet postTweet(TweetDTO tweetDTO) {
        User user = userRepository.findById(tweetDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Tweet tweet1 = new Tweet();
        tweet1.setContent(tweetDTO.getContent());
        tweet1.setUser(user);

        tweet1 = tweetRepository.save(tweet1);
        return tweet1;

    }

    public List<Tweet> getTimeline(Long userId) {
        return tweetRepository.findByUserId(userId);
    }
}
