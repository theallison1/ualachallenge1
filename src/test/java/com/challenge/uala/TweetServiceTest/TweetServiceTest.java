package com.challenge.uala.TweetServiceTest;

import com.challenge.uala.model.DtoUsuarios.UserDtoResponse;
import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.TweetDto.TweetDTO;
import com.challenge.uala.model.User;
import com.challenge.uala.repos.TweetRepository;
import com.challenge.uala.repos.UserRepository;
import com.challenge.uala.services.TweetServiceImpl.TweetServiceImpl;
import com.challenge.uala.services.UserService.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TweetServiceImplTest {

    @Mock
    private TweetRepository tweetRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private TweetServiceImpl tweetServiceImpl;

    private TweetDTO tweetDTO;
    private Tweet tweet;
    private User user;
    private UserDtoResponse userDtoResponse;

    @BeforeEach
    void setUp() {
        tweetDTO = new TweetDTO();
        tweetDTO.setUserId(1L);
        tweetDTO.setContent("Test tweet");

        user = new User();
        user.setId(1L);
        user.setUsername("user1");

        tweet = new Tweet();
        tweet.setId(1L);
        tweet.setContent("Test tweet");
        tweet.setUser(user);

        userDtoResponse = new UserDtoResponse();
        userDtoResponse.setId(1L);
        userDtoResponse.setUsername("user1");
    }

    @Test
    void testPostTweet() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(tweetRepository.save(any(Tweet.class))).thenReturn(tweet);

        Tweet result = tweetServiceImpl.postTweet(tweetDTO);

        assertNotNull(result);
        assertEquals("Test tweet", result.getContent());
        assertEquals(user, result.getUser());
    }

    @Test
    void testPostTweet_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> tweetServiceImpl.postTweet(tweetDTO));

        assertEquals("Usuario no encontrado", exception.getMessage());
    }

    @Test
    void testGetTimeline() {
        when(tweetRepository.findByUserId(1L)).thenReturn(Collections.singletonList(tweet));

        List<Tweet> timeline = tweetServiceImpl.getTimeline(1L);

        assertNotNull(timeline);
        assertEquals(1, timeline.size());
        assertEquals("Test tweet", timeline.get(0).getContent());
    }

    @Test
    void testGetTimelineForUser() {
        when(userService.getUserById(1L)).thenReturn(userDtoResponse);
        when(userService.getUsersFollowedByUser(1L)).thenReturn(Collections.singletonList(userDtoResponse));
        when(tweetRepository.findByUserInOrderByCreatedAtDesc(any(List.class))).thenReturn(Collections.singletonList(tweet));

        List<Tweet> timeline = tweetServiceImpl.getTimelineForUser(1L);

        assertNotNull(timeline);
        assertEquals(1, timeline.size());
        assertEquals("Test tweet", timeline.get(0).getContent());
    }
}