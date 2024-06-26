package com.challenge.uala.services.UserService;

import com.challenge.uala.model.DtoUsuarios.UserDTO;
import com.challenge.uala.model.DtoUsuarios.UserDtoResponse;
import com.challenge.uala.model.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);
    UserDtoResponse getUserById(Long userId);
    UserDtoResponse saveUser(UserDTO userDTO);
     void followUser(User user, User userToFollow);

    List<UserDtoResponse> getUsersFollowedByUser(Long userId);
}
