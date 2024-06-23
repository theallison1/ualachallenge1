package com.challenge.uala.services.UserService;

import com.challenge.uala.model.User;

public interface UserService {

    User findByUsername(String username);
}
