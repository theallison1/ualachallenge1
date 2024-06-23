package com.challenge.uala.services.UserServiceImple;

import com.challenge.uala.model.User;
import com.challenge.uala.repos.UserRepository;
import com.challenge.uala.services.UserService.UserService;
import org.springframework.stereotype.Service;

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
