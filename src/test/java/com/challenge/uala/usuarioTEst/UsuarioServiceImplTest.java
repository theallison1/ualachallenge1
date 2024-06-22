package com.challenge.uala.usuarioTEst;


import com.challenge.uala.model.DtoUsuarios.DtoUsuarios;
import com.challenge.uala.model.Usuarios;
import com.challenge.uala.repoUsuarios.UsuarioRepo;
import com.challenge.uala.serviceUsuarios.UsuarioServiceImpl;
import com.challenge.uala.serviceUsuarios.UsuariosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepo usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    private Usuarios usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = Usuarios.builder()
                .id(1L)
                .name("Juan")
                .lastName("Perez")
                .email("juan.perez@example.com")
                .birthDate(LocalDate.now())
                .phone("343434")
                .build();
    }

    @Test
    void testGetAllUsuarios() {
        List<Usuarios> usuarios = Collections.singletonList(usuario);
        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuarios> result = usuarioService.getAllUsuarios();
        assertEquals(1, result.size());
        assertEquals(usuario, result.get(0));
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testGetUsuarioById() {
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        Optional<Usuarios> result = usuarioService.getUsuarioById(usuario.getId());
        assertTrue(result.isPresent());
        assertEquals(usuario, result.get());
        verify(usuarioRepository, times(1)).findById(usuario.getId());
    }

    @Test
    void testCreateUsuario() {
        DtoUsuarios dtoUsuarios = new DtoUsuarios();
        dtoUsuarios.setLastName("espi");
        dtoUsuarios.setEmail("jajaja");
        dtoUsuarios.setBirthDate(LocalDate.now());
        dtoUsuarios.setName("nicoo");
        dtoUsuarios.setPhone("45455454");

        Usuarios result = usuarioService.createUsuario(dtoUsuarios);
        assertEquals(usuario, result);
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testUpdateUsuario() {
        Usuarios updatedUsuario = Usuarios.builder()
                .id(1L)
                .name("Juan")
                .lastName("Gomez")
                .email("juan.gomez@example.com")
                .birthDate(LocalDate.MIN).phone("222")
                .build();

        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuarios.class))).thenReturn(updatedUsuario);

        Optional<Usuarios> result = usuarioService.updateUsuario(usuario.getId(), updatedUsuario);
        assertTrue(result.isPresent());
        assertEquals(updatedUsuario, result.get());
        verify(usuarioRepository, times(1)).findById(usuario.getId());
        verify(usuarioRepository, times(1)).save(updatedUsuario);
    }

    @Test
    void testDeleteUsuario() {
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));

        boolean result = usuarioService.deleteUsuario(usuario.getId());
        assertTrue(result);
        verify(usuarioRepository, times(1)).findById(usuario.getId());
        verify(usuarioRepository, times(1)).delete(usuario);
    }

    @Test
    void testDeleteUsuarioNotFound() {
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.empty());

        boolean result = usuarioService.deleteUsuario(usuario.getId());
        assertFalse(result);
        verify(usuarioRepository, times(1)).findById(usuario.getId());
        verify(usuarioRepository, never()).delete(any(Usuarios.class));
    }
}
