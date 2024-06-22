package com.challenge.uala.serviceUsuarios;

import com.challenge.uala.model.DtoUsuarios.DtoUsuarios;
import com.challenge.uala.model.Usuarios;

import java.util.List;
import java.util.Optional;

public interface UsuariosService {
    List<Usuarios> getAllUsuarios();
    Optional<Usuarios> getUsuarioById(Long id);
    Usuarios createUsuario(DtoUsuarios usuario);
    Optional<Usuarios> updateUsuario(Long id, Usuarios usuarioDetails);
    boolean deleteUsuario(Long id);
    void followUser(Usuarios user, Usuarios userToFollow);
    List<Usuarios> getFollowing(Usuarios user);
    Usuarios findByUsername(String username);
}
