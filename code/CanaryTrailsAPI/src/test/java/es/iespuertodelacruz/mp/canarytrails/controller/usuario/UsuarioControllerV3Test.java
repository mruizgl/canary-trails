package es.iespuertodelacruz.mp.canarytrails.controller.usuario;

import es.iespuertodelacruz.mp.canarytrails.controller.v3.UsuarioControllerV3;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.UsuarioMapper;
import es.iespuertodelacruz.mp.canarytrails.service.FotoManagementService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerV3Test {

    @InjectMocks
    private UsuarioControllerV3 controller;

    @Mock private UsuarioService usuarioService;
    @Mock private UsuarioMapper usuarioMapper;
    @Mock private FotoManagementService fotoManagementService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("test");
        usuario.setCorreo("test@correo.com");
        usuario.setPassword("password");
    }

    @Test
    void testFindAllUsuarios() {
        when(usuarioService.findAll()).thenReturn(List.of(usuario));
        when(usuarioMapper.toDto(usuario)).thenReturn(
                new UsuarioSalidaDto(1, "test", "test@correo.com", "password", "token", null, true, "ROLE_USER", List.of(), List.of(), List.of(), List.of(), List.of(), "foto.png")
        );

        ResponseEntity<?> response = controller.findAllUsuarios();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testFindUsuarioById_Found() {
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(usuarioMapper.toDto(usuario)).thenReturn(
                new UsuarioSalidaDto(1, "test", "test@correo.com", "password", "token", null, true, "ROLE_USER", List.of(), List.of(), List.of(), List.of(), List.of(), "foto.png")
        );

        ResponseEntity<?> response = controller.findUsuarioById(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testFindUsuarioById_NotFound() {
        when(usuarioService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.findUsuarioById(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateUsuario_Success() {
        UsuarioEntradaCreateDto dto = new UsuarioEntradaCreateDto("test", "apellidos", "test@correo.com", true, "ROLE_USER");
        when(usuarioMapper.toEntityCreate(dto)).thenReturn(usuario);
        when(usuarioService.save(usuario)).thenReturn(usuario);
        when(usuarioMapper.toDto(usuario)).thenReturn(
                new UsuarioSalidaDto(1, "test", "test@correo.com", "password", "token", null, true, "ROLE_USER", List.of(), List.of(), List.of(), List.of(), List.of(), "foto.png")
        );

        ResponseEntity<?> response = controller.createUsuario(dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCreateUsuario_Failure() {
        UsuarioEntradaCreateDto dto = new UsuarioEntradaCreateDto("test", "apellidos", "badmail", true, "ROLE_USER");
        when(usuarioMapper.toEntityCreate(dto)).thenReturn(usuario);
        when(usuarioService.save(usuario)).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.createUsuario(dto);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("error"));
    }

    @Test
    void testUpdateUsuario_Success() {
        UsuarioEntradaUpdateDto dto = new UsuarioEntradaUpdateDto(1, "nuevo", "apellido", "nuevo@mail.com", true, "ROLE_USER");
        when(usuarioMapper.toEntityUpdate(dto)).thenReturn(usuario);
        when(usuarioService.update(usuario)).thenReturn(true);

        ResponseEntity<?> response = controller.updateUsuario(dto);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    void testUpdateUsuario_Failure() {
        UsuarioEntradaUpdateDto dto = new UsuarioEntradaUpdateDto(1, "nuevo", "apellido", "nuevo@mail.com", true, "ROLE_USER");
        when(usuarioMapper.toEntityUpdate(dto)).thenReturn(usuario);
        when(usuarioService.update(usuario)).thenThrow(new RuntimeException("fallo"));

        ResponseEntity<?> response = controller.updateUsuario(dto);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("fallo"));
    }

    @Test
    void testDeleteUsuario() {
        when(usuarioService.deleteById(1)).thenReturn(true);
        ResponseEntity<?> response = controller.deleteUsuario(1);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    void testUploadFile_Success() {
        MockMultipartFile file = new MockMultipartFile("file", "foto.jpg", "image/jpeg", "contenido".getBytes());
        when(fotoManagementService.save(file, "usuario")).thenReturn("usuario/foto.jpg");
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(usuarioService.uploadFoto(any())).thenReturn(true);

        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("usuario/foto.jpg", response.getBody());
    }

    @Test
    void testUploadFile_UsuarioNotFound() {
        MockMultipartFile file = new MockMultipartFile("file", "foto.jpg", "image/jpeg", "contenido".getBytes());
        when(fotoManagementService.save(file, "usuario")).thenReturn("usuario/foto.jpg");
        when(usuarioService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUploadFile_Exception() {
        MockMultipartFile file = new MockMultipartFile("file", "foto.jpg", "image/jpeg", "contenido".getBytes());
        when(fotoManagementService.save(file, "usuario")).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("error"));
    }
}
