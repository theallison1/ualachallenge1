package com.challenge.uala.services.UserService;

import com.challenge.uala.model.DtoUsuarios.UserDTO;
import com.challenge.uala.model.User;

public interface UserService {

    User findByUsername(String username);
    public User getUserById(Long id);
    public User saveUser(UserDTO userDTO);
    public void followUser(User user, User userToFollow);
}
