package com.challenge.uala.UserServiceTest;

import com.challenge.uala.model.DtoUsuarios.UserDTO;
import com.challenge.uala.model.DtoUsuarios.UserDtoResponse;
import com.challenge.uala.model.User;
import com.challenge.uala.repos.UserRepository;
import com.challenge.uala.services.UserServiceImple.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testGetUserById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserDtoResponse foundUser = userService.getUserById(1L);
        assertNotNull(foundUser);
        assertEquals("user1", foundUser.getUsername());
    }

    @Test
    void testGetUserByUsername() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));

        User foundUser = userService.findByUsername("user1");
        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
    }

    @Test
    void testSaveUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("user1");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername("user1");

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        when(userRepository.save(userCaptor.capture())).thenReturn(savedUser);

        UserDtoResponse result = userService.saveUser(userDTO);
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("user1", result.getUsername());

        User capturedUser = userCaptor.getValue();
        assertEquals("user1", capturedUser.getUsername());
    }

    @Test
    void testGetUsersFollowedByUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");

        User follower = new User();
        follower.setId(2L);
        follower.setUsername("follower1");

        user.setFollowers(Set.of(follower));
        follower.setFollowing(Set.of(user));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.findById(2L)).thenReturn(Optional.of(follower));

        Set<UserDtoResponse> followers = userService.getUsersFollowedByUser(1L).stream().collect(Collectors.toSet());

        assertNotNull(followers);
        assertEquals(1, followers.size());
        assertEquals("follower1", followers.iterator().next().getUsername());
    }
}
