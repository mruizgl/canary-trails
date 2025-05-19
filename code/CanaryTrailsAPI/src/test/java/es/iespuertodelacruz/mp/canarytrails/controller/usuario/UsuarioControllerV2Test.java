package es.iespuertodelacruz.mp.canarytrails.controller.usuario;

import es.iespuertodelacruz.mp.canarytrails.controller.v2.UsuarioControllerV2;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerV2Test {

    @InjectMocks
    private UsuarioControllerV2 controller;

    @Mock private UsuarioService usuarioService;
    @Mock private UsuarioMapper usuarioMapper;
    @Mock private FotoManagementService fotoManagementService;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("user");
        usuario.setVerificado(true); // <--- IMPRESCINDIBLE para evitar NPE

        Authentication auth = mock(Authentication.class);
        lenient().when(auth.getName()).thenReturn("user");

        SecurityContext context = mock(SecurityContext.class);
        lenient().when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);
    }


    private UsuarioSalidaDto mockUsuarioSalidaDto() {
        return new UsuarioSalidaDto(
                1, "user", "user@example.com", "password",
                "token123", new Date(), true, "ROLE_USER",
                null, null, null, null, null, "foto.jpg"
        );
    }

    @Test
    public void testFindUsuarioById_Success() {
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(usuarioMapper.toDto(usuario)).thenReturn(mockUsuarioSalidaDto());

        ResponseEntity<?> response = controller.findUsuarioById(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testFindUsuarioById_NotFound() {
        when(usuarioService.findById(1)).thenReturn(null);
        ResponseEntity<?> response = controller.findUsuarioById(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testFindUsuarioById_Forbidden() {
        usuario.setNombre("otro");
        when(usuarioService.findById(1)).thenReturn(usuario);
        ResponseEntity<?> response = controller.findUsuarioById(1);
        assertEquals(403, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("perfil de otro usuario"));
    }

    @Test
    public void testUpdateUsuario_Success() {
        UsuarioEntradaUpdateDto dto = new UsuarioEntradaUpdateDto(1, "user", "ape", "email",  true, "ROLE_USER");
        when(usuarioMapper.toEntityUpdate(dto)).thenReturn(usuario);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(usuarioService.update(usuario)).thenReturn(true);

        ResponseEntity<?> response = controller.updateUsuario(dto);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    public void testUpdateUsuario_Forbidden() {
        UsuarioEntradaUpdateDto dto = new UsuarioEntradaUpdateDto(1, "otro", "ape", "email", true, "ROLE_USER");
        Usuario otroUsuario = new Usuario();
        otroUsuario.setId(1);
        otroUsuario.setNombre("otro");

        when(usuarioMapper.toEntityUpdate(dto)).thenReturn(otroUsuario);

        ResponseEntity<?> response = controller.updateUsuario(dto);
        assertEquals(403, response.getStatusCodeValue());
    }

    @Test
    public void testUpdateUsuario_BadRequest() {
        UsuarioEntradaUpdateDto dto = new UsuarioEntradaUpdateDto(1, "user", "ape", "email", true, "ROLE_USER");
        when(usuarioMapper.toEntityUpdate(dto)).thenReturn(usuario);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(usuarioService.update(usuario)).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.updateUsuario(dto);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testUploadFile_Success() {
        MockMultipartFile file = new MockMultipartFile("file", "foto.jpg", "image/jpeg", "data".getBytes());
        when(fotoManagementService.save(file, "usuario")).thenReturn("usuario/foto.jpg");
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(usuarioService.uploadFoto(usuario)).thenReturn(true);

        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("usuario/foto.jpg", response.getBody());
    }

    @Test
    public void testUploadFile_UsuarioNotFound() {
        MockMultipartFile file = new MockMultipartFile("file", "foto.jpg", "image/jpeg", "data".getBytes());
        when(fotoManagementService.save(file, "usuario")).thenReturn("usuario/foto.jpg");
        when(usuarioService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testUploadFile_Forbidden() {
        MockMultipartFile file = new MockMultipartFile("file", "foto.jpg", "image/jpeg", "data".getBytes());
        Usuario otro = new Usuario();
        otro.setId(1);
        otro.setNombre("otro");

        when(fotoManagementService.save(file, "usuario")).thenReturn("usuario/foto.jpg");
        when(usuarioService.findById(1)).thenReturn(otro);

        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(403, response.getStatusCodeValue());
    }

    @Test
    public void testUploadFile_Exception() {
        MockMultipartFile file = new MockMultipartFile("file", "foto.jpg", "image/jpeg", "data".getBytes());
        when(fotoManagementService.save(file, "usuario")).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("No se pudo subir"));
    }
}
