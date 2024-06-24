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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tweets")

public class TweetController {
    private final TweetService tweetService;
    private final UserService userService;
    private final TweetMapper tweetMapper;

    @Autowired
    public TweetController(TweetService tweetService, UserService userService, TweetMapper tweetMapper) {
        this.tweetService = tweetService;
        this.userService = userService;
        this.tweetMapper = tweetMapper;
    }

    @PostMapping
    public ResponseEntity<TweetDTO> postTweet(@RequestHeader(value = "userId", required = false) Long headerUserId,
                                              @RequestParam(value = "userId", required = false) Long paramUserId,
                                              @RequestBody TweetDTO tweetDTO) {
        Long userId = UserIdentifierExtractor.extractUserId(headerUserId, paramUserId, tweetDTO != null ? tweetDTO.getUserId() : null);
        User user = userService.getUserById(userId);

        Tweet tweet = tweetMapper.toEntity(tweetDTO, user);
        Tweet createdTweet = tweetService.postTweet(tweetMapper.toDto(tweet));
        TweetDTO createdTweetDTO = tweetMapper.toDto(createdTweet);

        return ResponseEntity.ok(createdTweetDTO);
    }

    @GetMapping("/timeline")
    public ResponseEntity<List<TweetDTO>> getTimeline(@RequestHeader(value = "userId", required = false) Long headerUserId,
                                                      @RequestParam(value = "userId", required = false) Long paramUserId) {

        Long userId = UserIdentifierExtractor.extractUserId(headerUserId, paramUserId, null);
        User user = userService.getUserById(userId);

        List<TweetDTO> timelineDTO = tweetService.getTimeline(user.getId()).stream()
                .map(tweetMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(timelineDTO);
    }
}

