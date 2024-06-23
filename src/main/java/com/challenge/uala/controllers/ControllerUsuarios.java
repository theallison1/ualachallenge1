package com.challenge.uala.controllers;

import com.challenge.uala.model.DtoUsuarios.DtoUsuarios;
import com.challenge.uala.model.User;
import com.challenge.uala.services.UserService.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class ControllerUsuarios {


    private final UserService userService;
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);


    public ControllerUsuarios(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsuarios() {
        return userService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUsuarioById(@PathVariable Long id) {
        Optional<User> usuario = userService.getUsuarioById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUsuario(@RequestBody DtoUsuarios usuario) {
        LOGGER.info(usuario.getName());

        User user = userService.createUsuario(usuario);

        return user;
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUsuario(@PathVariable Long id, @RequestBody User usuarioDetails) {
        Optional<User> updatedUsuario = userService.updateUsuario(id, usuarioDetails);
        return updatedUsuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUsuario(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{username}/follow/{userToFollow}")
    public ResponseEntity<Void> followUser(@PathVariable String username, @PathVariable String userToFollow) {
        User user = userService.findByUsername(username);
        User followUser = userService.findByUsername(userToFollow);
        userService.followUser(user, followUser);
        return ResponseEntity.ok().build();
    }
}
