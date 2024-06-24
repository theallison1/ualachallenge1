package com.challenge.uala.controllers;

import com.challenge.uala.model.DtoUsuarios.UserDTO;
import com.challenge.uala.model.User;
import com.challenge.uala.services.UserService.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class ControllerUsuarios {

    @Autowired
    private final UserService userService;
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);


    public ControllerUsuarios(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/{id}")
    public ResponseEntity<User> getUsuarioById(@PathVariable Long id) {
        Optional<User> usuario = Optional.ofNullable(userService.getUserById(id));
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@Valid @RequestBody UserDTO userDTO) {
        User savedUser = userService.saveUser(userDTO);
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
