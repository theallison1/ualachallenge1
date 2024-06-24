package com.challenge.uala.UserServiceTest;

import com.challenge.uala.model.User;
import com.challenge.uala.repos.UserRepository;
import com.challenge.uala.services.UserServiceImple.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

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

        User foundUser = userService.getUserById(1L);
        assertNotNull(foundUser);
        assertEquals("user1", foundUser.getUsername());
    }

    @Test
    void testGetUserByUsername() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");

        when(userRepository.findByUsername("user1")).thenReturn(user);

        User foundUser = userService.findByUsername("user1");
        assertNotNull(foundUser);
        assertEquals(1L, foundUser.getId());
    }

//    @Test
//    void testSaveUser() {
//        User user = new User();
//        user.setId(1L);
//        user.setUsername("user1");
//
//        when(userRepository.save(user)).thenReturn(user);
//
//        User savedUser = userService.saveUser(user);
//        assertNotNull(savedUser);
//        assertEquals("user1", savedUser.getUsername());
//    }
}
