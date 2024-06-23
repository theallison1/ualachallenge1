package com.challenge.uala.TweetControllerTest;


import com.challenge.uala.model.Tweet;
import com.challenge.uala.services.TweetService.TweetService;
import com.challenge.uala.controllers.TweetController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
public class TweetControllerTest {

    @Mock
    private TweetService tweetService;

    @InjectMocks
    private TweetController tweetController;

    @Test
    void testPostTweet() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(tweetController).build();

        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setContent("Hello World");
        tweet.setCreatedAt(LocalDateTime.now());

        when(tweetService.postTweet(any(Tweet.class))).thenReturn(tweet);

        mockMvc.perform(post("/tweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"Hello World\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is("Hello World")));
    }

    @Test
    void testGetTimeline() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(tweetController).build();

        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setContent("Tweet 1");
        tweet.setCreatedAt(LocalDateTime.now());

        when(tweetService.getTimeline(1L)).thenReturn(List.of(tweet));

        mockMvc.perform(get("/tweets/timeline/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].content", is("Tweet 1")));
    }
}
