package com.challenge.uala.serviceUsuarios;

import com.challenge.uala.model.DtoUsuarios.DtoUsuarios;
import com.challenge.uala.model.Usuarios;
import com.challenge.uala.repoUsuarios.UsuarioRepo;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
@Service
public class UsuarioServiceImpl implements UsuariosService{

    private final UsuarioRepo usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepo usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public List<Usuarios> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuarios> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuarios createUsuario(DtoUsuarios usuario) {
        return usuarioRepository.save(
                Usuarios.builder()
                        .name(usuario.getName())
                        .lastName(usuario.getLastName())
                        .email(usuario.getEmail())
                        .birthDate(usuario.getBirthDate())
                        .phone(usuario.getPhone())
                        .build()
        );
    }

    @Override
    public Optional<Usuarios> updateUsuario(Long id, Usuarios usuarioDetails) {
        Optional<Usuarios> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuarios existingUsuario = optionalUsuario.get();
            existingUsuario.setName(usuarioDetails.getName());
            existingUsuario.setLastName(usuarioDetails.getLastName());
            existingUsuario.setEmail(usuarioDetails.getEmail());
            existingUsuario.setBirthDate(usuarioDetails.getBirthDate());
            existingUsuario.setPhone(usuarioDetails.getPhone());
            return Optional.of(usuarioRepository.save(existingUsuario));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteUsuario(Long id) {
        Optional<Usuarios> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            usuarioRepository.delete(optionalUsuario.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void followUser(Usuarios user, Usuarios userToFollow) {
        user.getFollowing().add(userToFollow);
        usuarioRepository.save(user);
    }

    @Override
    public List<Usuarios> getFollowing(Usuarios user) {
        return List.copyOf(user.getFollowing());
    }


}
