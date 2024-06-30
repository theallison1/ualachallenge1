package com.challenge.uala.userMapperTest;


import com.challenge.uala.mapper.UserMapper;
import com.challenge.uala.model.DtoUsuarios.UserDtoResponse;
import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.User;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserMapperTest {


    @Test
public void testToDto() {
    // Crear datos de prueba
    User user = new User();
    user.setId(1L);
    user.setUsername("testuser");

    Tweet tweet1 = new Tweet();
    tweet1.setId(1L);
    Tweet tweet2 = new Tweet();
    tweet2.setId(2L);
    List<Tweet> tweets = new ArrayList<>();
    tweets.add(tweet1);
    tweets.add(tweet2);
    user.setTweets( tweets);

    User follower1 = new User();
    follower1.setId(2L);
    follower1.setUsername("follower1");
    User follower2 = new User();
    follower2.setId(3L);
    follower2.setUsername("follower2");
    Set<User> followers = new HashSet<>();
    followers.add(follower1);
    followers.add(follower2);
    user.setFollowers(followers);

    // Llamar al método toDto
    UserDtoResponse dto = UserMapper.toDto(user);

    // Verificar resultados
    assertNotNull(dto);
    assertEquals(user.getId(), dto.getId());
    assertEquals(user.getUsername(), dto.getUsername());
    assertEquals(2, dto.getTweetIds().size());
    assertEquals(2, dto.getFollowerIds().size());
    assertEquals(2, dto.getFollowers().size());
}

@Test
public void testToDtoWithNulls() {
    // Crear datos de prueba con nulls
    User user = new User();
    user.setId(1L);
    user.setUsername("testuser");
    user.setTweets(null);
    user.setFollowers(null);

    // Llamar al método toDto
    UserDtoResponse dto = UserMapper.toDto(user);

    // Verificar resultados
    assertNotNull(dto);
    assertEquals(user.getId(), dto.getId());
    assertEquals(user.getUsername(), dto.getUsername());
    assertEquals(Collections.emptySet(), dto.getTweetIds());
    assertEquals(Collections.emptySet(), dto.getFollowerIds());
    assertEquals(Collections.emptySet(), dto.getFollowers());
}
}