package com.challenge.uala.controllers;


import com.challenge.uala.mapper.TweetMapper;
import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.TweetDto.TweetDTO;
import com.challenge.uala.model.User;
import com.challenge.uala.services.TweetService.TweetService;
import com.challenge.uala.services.UserService.UserService;
import com.challenge.uala.util.UserIdentifierExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tweets")

public class TweetController {
    private TweetService tweetService;
    private UserService userService;
    private final TweetMapper tweetMapper;

    @Autowired
    public TweetController(TweetService tweetService, UserService userService, TweetMapper tweetMapper) {
        this.tweetService = tweetService;
        this.userService = userService;
        this.tweetMapper = tweetMapper;
    }


    @PostMapping
        public ResponseEntity<Tweet> postTweet(@RequestHeader(value = "userId", required = false) Long headerUserId,
                                               @RequestParam(value = "userId", required = false) Long paramUserId,
                                               @RequestBody TweetDTO tweetDTO) {

        Long userId = UserIdentifierExtractor.extractUserId(headerUserId, paramUserId, tweetDTO != null ? tweetDTO.getUserId() : null);
        Optional<User> userOpt = Optional.ofNullable(userService.getUserById(userId));

        if (userOpt.isPresent()) {


            Tweet createdTweet = tweetService.postTweet(tweetDTO);

            Tweet tweet = new Tweet();
            tweet.setContent(createdTweet.getContent());
            tweet.setUser(createdTweet.getUser());

            return ResponseEntity.ok(tweet);
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/timeline")
    public ResponseEntity<List<TweetDTO>> getTimeline(@RequestHeader(value = "userId", required = false) Long headerUserId,
                                                      @RequestParam(value = "userId", required = false) Long paramUserId) {
        Long userId = UserIdentifierExtractor.extractUserId(headerUserId, paramUserId, null);
        Optional<User> userOpt = Optional.ofNullable(userService.getUserById(userId));

        if (userOpt.isPresent()) {
            List<Tweet> timeline = tweetService.getTimeline(userOpt.get().getId());
            List<TweetDTO> timelineDTO = timeline.stream().map(tweet -> {
                TweetDTO dto = new TweetDTO();
                dto.setUserId(tweet.getId());
                dto.setContent(tweet.getContent());
                dto.setCreatedAt(tweet.getCreatedAt());
                dto.setUserId(tweet.getUser().getId());
                return dto;
            }).collect(Collectors.toList());

            return ResponseEntity.ok(timelineDTO);
        }
        return ResponseEntity.notFound().build();

    }

}

