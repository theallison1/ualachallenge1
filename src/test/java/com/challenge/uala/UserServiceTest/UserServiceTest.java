package com.challenge.uala.UserServiceTest;

import com.challenge.uala.model.DtoUsuarios.UserDTO;
import com.challenge.uala.model.DtoUsuarios.UserDtoResponse;
import com.challenge.uala.model.User;
import com.challenge.uala.repos.UserRepository;
import com.challenge.uala.services.UserService.UserService;
import com.challenge.uala.services.UserServiceImple.UserServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @Transactional(readOnly = true)
    public void testGetUserByIdSuccess() {
        // Mock de usuario existente
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("usuario1");

        // Configurar el comportamiento del repositorio
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        // Llamada al método
        UserDtoResponse result = userService.getUserById(userId);

        // Verificaciones
        Assertions.assertNotNull(result);
        Assertions.assertEquals(userId, result.getId());
        Assertions.assertEquals(user.getUsername(), result.getUsername());

        // Verificar que el método del repositorio se llamó una vez con el userId
        verify(userRepository, times(1)).findById(userId);
    }




    @Test
    void testGetUserByUsername() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user1");

        when(userRepository.findByUsername("user1")).thenReturn(Optional.of(user));

        User foundUser = userService.findByUsername("user1");
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(1L, foundUser.getId());
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
        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.getId());
        Assertions.assertEquals("user1", result.getUsername());

        User capturedUser = userCaptor.getValue();
        Assertions.assertEquals("user1", capturedUser.getUsername());
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

        Assertions.assertNotNull(followers);
        Assertions.assertEquals(1, followers.size());
        Assertions.assertEquals("follower1", followers.iterator().next().getUsername());
    }

    @Test
    @Transactional
    public void testFollowUser() {
        // Mock de usuarios
        User user = new User();
        user.setId(1L);
        user.setUsername("usuario1");

        User userToFollow = new User();
        userToFollow.setId(2L);
        userToFollow.setUsername("usuario2");

        // Llamada al método
        userService.followUser(user, userToFollow);

        // Verificación de que los métodos se llamaron correctamente
        verify(userRepository, times(1)).save(user);
        verify(userRepository, times(1)).save(userToFollow);
    }
}
