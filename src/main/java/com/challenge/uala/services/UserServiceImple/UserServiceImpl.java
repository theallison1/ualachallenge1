package com.challenge.uala.services.UserServiceImple;

import com.challenge.uala.model.DtoUsuarios.UserDTO;
import com.challenge.uala.model.DtoUsuarios.UserDtoResponse;
import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.User;
import com.challenge.uala.repos.TweetRepository;
import com.challenge.uala.repos.UserRepository;
import com.challenge.uala.services.UserService.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final TweetRepository tweetRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);



    public UserServiceImpl(UserRepository userRepository, TweetRepository tweetRepository) {
        this.userRepository = userRepository;

        this.tweetRepository = tweetRepository;
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }



    @Override
    @Transactional(readOnly = true)
    public UserDtoResponse getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + userId));

        return mapUserToDtoResponse(user);
    }

    private UserDtoResponse mapUserToDtoResponse(User user) {
        UserDtoResponse dto = new UserDtoResponse();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        // Cargar los IDs de los tweets
        dto.setTweetIds(mapTweetIds(user));

        // Cargar los followers completos
        dto.setFollowers(mapFollowers(user));

        // Cargar solo los IDs de los followers
        dto.setFollowerIds(mapFollowerIds(user));

        return dto;
    }

    private Set<Long> mapTweetIds(User user) {
        return user.getTweets().stream()
                .map(Tweet::getId)
                .collect(Collectors.toSet());
    }

    private Set<UserDtoResponse> mapFollowers(User user) {
        return user.getFollowers().stream()
                .map(this::mapFollowerToDto)
                .collect(Collectors.toSet());
    }

    private UserDtoResponse mapFollowerToDto(User follower) {
        UserDtoResponse followerDto = new UserDtoResponse();
        followerDto.setId(follower.getId());
        followerDto.setUsername(follower.getUsername());
        // Cargar los IDs de los tweets de cada seguidor
        followerDto.setTweetIds(mapTweetIds(follower));
        // Cargar solo los IDs de los followers de cada seguidor
        followerDto.setFollowerIds(mapFollowerIds(follower));
        return followerDto;
    }

    private Set<Long> mapFollowerIds(User user) {
        return user.getFollowers().stream()
                .map(User::getId)
                .collect(Collectors.toSet());
    }




    @Override
    @Transactional
    public UserDtoResponse saveUser(UserDTO userDTO) {
        // Crear una instancia de User y asignar el username desde el UserDTO
        User user = new User();
        user.setUsername(userDTO.getUsername());

        // Guardar el usuario en el repositorio
        user = userRepository.save(user);

        // Crear un UserDtoResponse a partir del User guardado
        UserDtoResponse response = new UserDtoResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());

        // Obtener tweetIds y followerIds si es necesario
        Set<Long> tweetIds = user.getTweets() != null ?
                user.getTweets().stream()
                        .map(Tweet::getId)
                        .collect(Collectors.toSet()) :
                Collections.emptySet();

        response.setTweetIds(tweetIds);

        return response;
    }

    @Override
    @Transactional
    public void followUser(User user, User userToFollow) {
        if (!user.getFollowing().contains(userToFollow)) {
            LOGGER.info("Adding {} to {}'s following list", userToFollow.getUsername(), user.getUsername());
            user.getFollowing().add(userToFollow);
            userToFollow.getFollowers().add(user);
            userRepository.save(user);
            userRepository.save(userToFollow);
            LOGGER.info("Successfully followed: {} -> {}", user.getUsername(), userToFollow.getUsername());
        } else {
            LOGGER.info("{} is already following {}", user.getUsername(), userToFollow.getUsername());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDtoResponse> getUsersFollowedByUser(Long userId) {
        try {
            // Obtener el usuario por su ID
            UserDtoResponse userDtoResponse = this.getUserById(userId);

            // Obtener los seguidores del usuario
            Set<UserDtoResponse> followers = userDtoResponse.getFollowers();

            // Convertir los seguidores a UserDtoResponse

            return followers.stream()
                    .map(follower -> this.getUserById(follower.getId()))
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException e) {
            // Manejar la excepción si el usuario no se encuentra
            throw new RuntimeException("No se pudo encontrar el usuario con ID: " + userId, e);
        }
    }
}