package com.challenge.uala.services.UserServiceImple;

import com.challenge.uala.model.DtoUsuarios.UserDTO;
import com.challenge.uala.model.Tweet;

import com.challenge.uala.model.User;
import com.challenge.uala.repos.TweetRepository;
import com.challenge.uala.repos.UserRepository;
import com.challenge.uala.services.UserService.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;

    public UserServiceImpl(UserRepository usuarioRepository, TweetRepository tweetRepository) {
        this.userRepository = usuarioRepository;
        this.tweetRepository = tweetRepository;
    }

    public User getUserById(Long id) {

        return userRepository.findById(id).orElse(null);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User saveUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user = userRepository.save(user);
        return user;

    }
    private UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setTweetIds(user.getTweets().stream().map(Tweet::getId).collect(Collectors.toSet()));
        userDTO.setFollowerIds(user.getFollowers().stream().map(User::getId).collect(Collectors.toSet()));
        return userDTO;
    }

    // Método para seguir a otro usuario
    public void followUser(User user, User userToFollow) {
        user.getFollowers().add(userToFollow); // Añade userToFollow a los seguidores de user
        userRepository.save(user); // Guarda el usuario actualizado en la base de datos
    }
    public List<User> getUsersFollowedByUser(Long userId) {
        User user = getUserById(userId);
        return new ArrayList<>(user.getFollowers());
    }

}
