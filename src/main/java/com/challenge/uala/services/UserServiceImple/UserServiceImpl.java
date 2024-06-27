package com.challenge.uala.services.UserServiceImple;

import com.challenge.uala.model.DtoUsuarios.UserDTO;
import com.challenge.uala.model.DtoUsuarios.UserDtoResponse;
import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.User;
import com.challenge.uala.repos.UserRepository;
import com.challenge.uala.services.UserService.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con nombre de usuario: " + username));
    }

    @Override
    @Transactional
    public UserDtoResponse saveUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user = userRepository.save(user);

        return mapUserToDtoResponse(user);
    }

    @Override
    @Transactional
    public void followUser(User user, User userToFollow) {
        if (!user.getFollowing().contains(userToFollow)) {
            LOGGER.info("Añadiendo {} a la lista de seguidos de {}", userToFollow.getUsername(), user.getUsername());
            user.getFollowing().add(userToFollow);
            userToFollow.getFollowers().add(user);
            userRepository.save(user);
            userRepository.save(userToFollow);
            LOGGER.info("Seguimiento exitoso: {} -> {}", user.getUsername(), userToFollow.getUsername());
        } else {
            LOGGER.info("{} ya está siguiendo a {}", user.getUsername(), userToFollow.getUsername());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserDtoResponse getUserById(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + userId));

            return mapUserToDtoResponse(user);
        } catch (EntityNotFoundException e) {
            // Loguear la excepción o manejarla según sea necesario
            LOGGER.error("Usuario no encontrado con id: {}", userId, e);
            throw e; // Re-lanzar la excepción para que sea manejada por el controlador o capa superior
        } catch (DataAccessException e) {
            LOGGER.error("Excepción de acceso a datos al buscar usuario con id: {}", userId, e);
            throw new RuntimeException("Error al acceder a datos al buscar usuario", e); // Ejemplo de manejo adicional
        } catch (Exception e) {
            LOGGER.error("Otra excepción al buscar usuario con id: {}", userId, e);
            throw new RuntimeException("Error inesperado al buscar usuario", e); // Manejo de excepciones generales
        }
    }


    @Override
    @Transactional(readOnly = true)
    public List<UserDtoResponse> getUsersFollowedByUser(Long userId) {
        try {
            UserDtoResponse userDtoResponse = this.getUserById(userId);
            Set<UserDtoResponse> followers = userDtoResponse.getFollowers();

            return followers.stream()
                    .map(follower -> getUserById(follower.getId()))
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("No se pudo encontrar el usuario con ID: " + userId, e);
        }
    }

    private UserDtoResponse mapUserToDtoResponse(User user) {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        // Verificar si user.getTweets() es nulo o vacío antes de mapear los IDs de tweets
        if (user.getTweets() != null && !user.getTweets().isEmpty()) {
            dto.setTweetIds(user.getTweets().stream()
                    .map(Tweet::getId)
                    .collect(Collectors.toSet()));
        } else {
            dto.setTweetIds(Collections.emptySet());  // o null, dependiendo de cómo manejes el vacío
        }

        // Verificar si user.getFollowers() es nulo o vacío antes de mapear los seguidores
        if (user.getFollowers() != null && !user.getFollowers().isEmpty()) {
            dto.setFollowers(user.getFollowers().stream()
                    .map(this::mapFollowerToDto)
                    .collect(Collectors.toSet()));
            dto.setFollowerIds(user.getFollowers().stream()
                    .map(User::getId)
                    .collect(Collectors.toSet()));
        } else {
            dto.setFollowers(Collections.emptySet());  // o null, dependiendo de cómo manejes el vacío
            dto.setFollowerIds(Collections.emptySet());  // o null, dependiendo de cómo manejes el vacío
        }

        return dto;
    }
    private UserDtoResponse mapFollowerToDto(User follower) {
        UserDtoResponse followerDto = new UserDtoResponse();
        followerDto.setId(follower.getId());
        followerDto.setUsername(follower.getUsername());
        followerDto.setTweetIds(follower.getTweets().stream()
                .map(Tweet::getId)
                .collect(Collectors.toSet()));
        followerDto.setFollowerIds(follower.getFollowers().stream()
                .map(User::getId)
                .collect(Collectors.toSet()));
        return followerDto;
    }
}
