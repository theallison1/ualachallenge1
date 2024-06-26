package com.challenge.uala.mapper;

import com.challenge.uala.model.DtoUsuarios.UserDtoResponse;
import com.challenge.uala.model.User;import com.challenge.uala.model.Tweet;

import java.util.Collections;
import java.util.stream.Collectors;
public class UserMapper {
    public static UserDtoResponse toDto(User user) {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setTweetIds(user.getTweets() != null ?
                user.getTweets().stream()
                        .map(Tweet::getId)
                        .collect(Collectors.toSet()) :
                Collections.emptySet());
        dto.setFollowerIds(user.getFollowers() != null ?
                user.getFollowers().stream()
                        .map(User::getId)
                        .collect(Collectors.toSet()) :
                Collections.emptySet());
        dto.setFollowers(user.getFollowers() != null ?
                user.getFollowers().stream()
                        .map(UserMapper::toDtoSummary)
                        .collect(Collectors.toSet()) :
                Collections.emptySet());
        return dto;
    }

    private static UserDtoResponse toDtoSummary(User user) {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        // Optionally include other fields
        return dto;
    }
}
