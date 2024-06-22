package com.challenge.uala.serviceUsuarios;

import com.challenge.uala.model.DtoUsuarios.DtoUsuarios;
import com.challenge.uala.model.User;
import com.challenge.uala.repoUsuarios.UserRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository usuarioRepository) {
        this.userRepository = usuarioRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    @Override
    public User findByUsername(String username) {
        return null;
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
