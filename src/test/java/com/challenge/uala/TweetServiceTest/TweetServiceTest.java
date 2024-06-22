package com.challenge.uala.TweetServiceTest;

import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.User;
import com.challenge.uala.repoUsuarios.TweetRepository;
import com.challenge.uala.repoUsuarios.UserRepository;
import com.challenge.uala.serviceUsuarios.TweetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TweetServiceTest {

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TweetService tweetService;

    @Test
    void testPostTweet() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setContent("Hello World");
        tweet.setCreatedAt(LocalDateTime.now());

        when(tweetRepository.save(tweet)).thenReturn(tweet);

        Tweet postedTweet = tweetService.postTweet(tweet);
        assertNotNull(postedTweet);
        assertEquals("Hello World", postedTweet.getContent());
    }

    @Test
    void testGetTimeline() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("user1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("user2");

        user1.setFollowers(Set.of(user2));

        Tweet tweet1 = new Tweet();
        tweet1.setId(1L);
        tweet1.setContent("Tweet 1");
        tweet1.setUser(user2);
        tweet1.setCreatedAt(LocalDateTime.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(tweetRepository.findByUserInOrderByCreatedAtDesc(List.of(user2))).thenReturn(List.of(tweet1));

        List<Tweet> timeline = tweetService.getTimeline(1L);
        assertNotNull(timeline);
        assertEquals(1, timeline.size());
        assertEquals("Tweet 1", timeline.get(0).getContent());
    }
}