package com.challenge.uala.controllerUsuarios;

import com.challenge.uala.model.Usuarios;
import com.challenge.uala.serviceUsuarios.UsuariosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ControllerUsuarios {


    private final UsuariosService usuariosService;

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
    public Usuarios createUsuario(@RequestBody Usuarios usuario) {
        return usuariosService.createUsuario(usuario);
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
}
