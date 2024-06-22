package com.challenge.uala.serviceUsuarios;

import com.challenge.uala.model.DtoUsuarios.DtoUsuarios;
import com.challenge.uala.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findByUsername(String username);
}
