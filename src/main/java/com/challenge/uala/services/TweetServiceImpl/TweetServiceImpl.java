package com.challenge.uala.services.TweetServiceImpl;

import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.TweetDto.TweetDTO;
import com.challenge.uala.model.User;
import com.challenge.uala.repos.TweetRepository;
import com.challenge.uala.repos.UserRepository;
import com.challenge.uala.services.TweetService.TweetService;
import com.challenge.uala.services.UserService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

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
    public List<Tweet> getTimelineForUser(Long userId) {
        User user = userService.getUserById(userId);
        List<User> following = userService.getUsersFollowedByUser(userId);
        following.add(user); // Incluye los propios tweets del usuario

        return tweetRepository.findByUserInOrderByCreatedAtDesc(new ArrayList<>(following));

    }
}
