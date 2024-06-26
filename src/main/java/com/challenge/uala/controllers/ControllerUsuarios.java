package com.challenge.uala.controllers;

import com.challenge.uala.model.DtoUsuarios.UserDTO;
import com.challenge.uala.model.DtoUsuarios.UserDtoResponse;
import com.challenge.uala.model.User;
import com.challenge.uala.services.UserService.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class ControllerUsuarios {

    private final UserService userService;
    private static final Logger LOGGER = LogManager.getLogger(ControllerUsuarios.class);


    public ControllerUsuarios(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/{id}")
    public ResponseEntity<UserDtoResponse> getUsuarioById(@PathVariable Long id) {
        try {
            UserDtoResponse usuario = userService.getUserById(id);
            return ResponseEntity.ok(usuario);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<UserDtoResponse> saveUser(@Valid @RequestBody UserDTO userDTO) {
        UserDtoResponse savedUser = userService.saveUser(userDTO);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/{username}/follow/{userToFollow}")
    public ResponseEntity<Void> followUser(@PathVariable String username, @PathVariable String userToFollow) {
        LOGGER.info(username,userToFollow);
        LOGGER.info("-----------------------------------");

        User user = userService.findByUsername(username);
        User followUser = userService.findByUsername(userToFollow);

        if (user == null || followUser == null) {
            return ResponseEntity.notFound().build();
        }

        userService.followUser(user, followUser);
        return ResponseEntity.ok().build();
    }





}
