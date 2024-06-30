package com.challenge.uala.controllers;


import com.challenge.uala.model.DtoUsuarios.UserDTO;
import com.challenge.uala.model.DtoUsuarios.UserDtoResponse;
import com.challenge.uala.model.User;
import com.challenge.uala.services.UserService.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/usuarios")
public class ControllerUsuarios {

    private final UserService userService;
    private static final Logger LOGGER = LogManager.getLogger(ControllerUsuarios.class);


    public ControllerUsuarios(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserDtoResponse user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario no encontrado con id" + id);
        }
        return ResponseEntity.ok(user);
    }


    @PostMapping("/save")
    public ResponseEntity<UserDtoResponse> saveUser(@Valid @RequestBody UserDTO userDTO) {
        UserDtoResponse savedUser = userService.saveUser(userDTO);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/{username}/follow/{userToFollow}")
    public ResponseEntity<?> followUser(@PathVariable String username, @PathVariable String userToFollow) {
        LOGGER.info("Attempting to follow: {} -> {}", username, userToFollow);
        LOGGER.info("-----------------------------------");

        User user = userService.findByUsername(username);
        User followUser = userService.findByUsername(userToFollow);

        if (user == null || followUser == null) {
            LOGGER.error("Usuario no encontrado : {} or {}", username, userToFollow);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("usuario no encontrado con nombre" +username);

        }

        userService.followUser(user, followUser);
        return ResponseEntity.ok().build();
    }




}
