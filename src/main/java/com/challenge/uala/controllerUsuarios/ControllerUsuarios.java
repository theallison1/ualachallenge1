package com.challenge.uala.controllerUsuarios;

import com.challenge.uala.model.DtoUsuarios.DtoUsuarios;
import com.challenge.uala.model.Usuarios;
import com.challenge.uala.serviceUsuarios.UsuariosService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class ControllerUsuarios {


    private final UsuariosService usuariosService;
    private static final Logger LOGGER = LogManager.getLogger(UsuariosService.class);


    public ControllerUsuarios(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping
    public List<Usuarios> getUsuarios() {
        return usuariosService.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuarios> getUsuarioById(@PathVariable Long id) {
        Optional<Usuarios> usuario = usuariosService.getUsuarioById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuarios createUsuario(@RequestBody DtoUsuarios usuario) {
        LOGGER.info(usuario.getName());

        Usuarios usuarios = usuariosService.createUsuario(usuario);

        return usuarios;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuarios> updateUsuario(@PathVariable Long id, @RequestBody Usuarios usuarioDetails) {
        Optional<Usuarios> updatedUsuario = usuariosService.updateUsuario(id, usuarioDetails);
        return updatedUsuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        boolean isDeleted = usuariosService.deleteUsuario(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PostMapping("/{username}/follow/{userToFollow}")
    public ResponseEntity<Void> followUser(@PathVariable String username, @PathVariable String userToFollow) {
        Usuarios user = usuariosService.findByUsername(username);
        Usuarios followUser = usuariosService.findByUsername(userToFollow);
        usuariosService.followUser(user, followUser);
        return ResponseEntity.ok().build();
    }
}
