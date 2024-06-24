package com.challenge.uala.mapper;

import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.TweetDto.TweetDTO;
import com.challenge.uala.model.User;
import org.springframework.stereotype.Component;

@Component
public class TweetMapper {

    public TweetDTO toDto(Tweet tweet) {
        TweetDTO dto = new TweetDTO();
        dto.setUserId(tweet.getId());
        dto.setContent(tweet.getContent());
        dto.setCreatedAt(tweet.getCreatedAt());
        dto.setUserId(tweet.getUser().getId());
        return dto;
    }

    public Tweet toEntity(TweetDTO dto, User user) {
        Tweet tweet = new Tweet();
        tweet.setContent(dto.getContent());
        tweet.setUser(user);
        return tweet;
    }
}
