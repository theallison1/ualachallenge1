package com.challenge.uala.repoUsuarios;

import com.challenge.uala.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuarios,Long> {
}
