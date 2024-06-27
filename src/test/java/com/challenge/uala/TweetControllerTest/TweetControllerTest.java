package com.challenge.uala.TweetControllerTest;


import com.challenge.uala.controllers.TweetController;
import com.challenge.uala.mapper.TweetMapper;
import com.challenge.uala.model.DtoUsuarios.UserDtoResponse;
import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.TweetDto.TweetDTO;
import com.challenge.uala.model.User;
import com.challenge.uala.services.TweetService.TweetService;
import com.challenge.uala.services.UserService.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TweetControllerTest {

    @Mock
    private TweetService tweetService;

    @Mock
    private UserService userService;

    @Mock
    private TweetMapper tweetMapper;

    @InjectMocks
    private TweetController tweetController;

    private UserDtoResponse userDtoResponse;
    private Tweet tweet;
    private TweetDTO tweetDTO;

    @BeforeEach
    void setUp() {
        userDtoResponse = new UserDtoResponse();
        userDtoResponse.setId(1L);
        userDtoResponse.setUsername("user1");

        tweet = new Tweet();
        tweet.setId(1L);
        tweet.setContent("Test tweet");
        tweet.setUser(new User());

        tweetDTO = new TweetDTO();
        tweetDTO.setUserId(1L);
        tweetDTO.setContent("Test tweet");
    }

    @Test
    void testPostTweet() {
        when(userService.getUserById(1L)).thenReturn(userDtoResponse);
        when(tweetService.postTweet(any(TweetDTO.class))).thenReturn(tweet);

        ResponseEntity<Tweet> response = tweetController.postTweet(1L, null, tweetDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test tweet", response.getBody().getContent());
    }

    @Test
    void testPostTweet_UserNotFound() {
        when(userService.getUserById(1L)).thenReturn(null);

        ResponseEntity<Tweet> response = tweetController.postTweet(1L, null, tweetDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetTimeline() {
        when(userService.getUserById(1L)).thenReturn(userDtoResponse);
        when(tweetService.getTimeline(1L)).thenReturn(Collections.singletonList(tweet));

        ResponseEntity<List<TweetDTO>> response = tweetController.getTimeline(1L, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Test tweet", response.getBody().get(0).getContent());
    }

    @Test
    void testGetTimeline_UserNotFound() {
        when(userService.getUserById(1L)).thenReturn(null);

        ResponseEntity<List<TweetDTO>> response = tweetController.getTimeline(1L, null);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}