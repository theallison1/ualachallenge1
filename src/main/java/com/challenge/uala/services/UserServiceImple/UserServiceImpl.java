package com.challenge.uala.services.UserServiceImple;

import com.challenge.uala.model.DtoUsuarios.UserDTO;
import com.challenge.uala.model.DtoUsuarios.UserDtoResponse;
import com.challenge.uala.model.Tweet;
import com.challenge.uala.model.User;
import com.challenge.uala.repos.TweetRepository;
import com.challenge.uala.repos.UserRepository;
import com.challenge.uala.services.UserService.UserService;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public UserServiceImpl(UserRepository userRepository, TweetRepository tweetRepository) {
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @PersistenceContext
    private EntityManager entityManager;

    // ...

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
        dto.setTweetIds(user.getTweets().stream()
                .map(Tweet::getId)
                .collect(Collectors.toSet()));

        // Cargar los followers completos
        dto.setFollowers(user.getFollowers().stream()
                .map(this::mapUserToDtoResponse)
                .collect(Collectors.toSet()));

        // Opcional: Cargar solo los IDs de los followers
        dto.setFollowerIds(user.getFollowers().stream()
                .map(User::getId)
                .collect(Collectors.toSet()));

        return dto;
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
        Set<Long> tweetIds = user.getTweets().stream()
                .map(Tweet::getId)
                .collect(Collectors.toSet());

        response.setTweetIds(tweetIds);

        return response;
    }

    @Override
    @Transactional
    public void followUser(User user, User userToFollow) {
        user.getFollowers().add(userToFollow); // Añade userToFollow a los seguidores de user
        userRepository.save(user); // Guarda el usuario actualizado en la base de datos
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
            List<UserDtoResponse> followersDtoResponses = followers.stream()
                    .map(follower -> this.getUserById(follower.getId()))
                    .collect(Collectors.toList());

            return followersDtoResponses;
        } catch (EntityNotFoundException e) {
            // Manejar la excepción si el usuario no se encuentra
            throw new RuntimeException("No se pudo encontrar el usuario con ID: " + userId, e);
        }
    }
}