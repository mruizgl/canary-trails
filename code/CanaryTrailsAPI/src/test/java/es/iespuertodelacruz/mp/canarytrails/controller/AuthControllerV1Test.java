package es.iespuertodelacruz.mp.canarytrails.controller.v1;

import es.iespuertodelacruz.mp.canarytrails.dto.usuario.auth.UsuarioLoginDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.auth.UsuarioRegisterDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.repository.UsuarioRepository;
import es.iespuertodelacruz.mp.canarytrails.security.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerV1Test {

    @InjectMocks
    private AuthControllerV1 authController;

    @Mock
    private AuthService authService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_Success() {
        UsuarioRegisterDto dto = new UsuarioRegisterDto("user", "user@example.com", "password123");

        when(authService.register(any())).thenReturn(true);

        ResponseEntity<?> response = authController.register(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Usuario registrado"));
    }

    @Test
    public void testRegister_Failure() {
        UsuarioRegisterDto dto = new UsuarioRegisterDto("user", "user@example.com", "password123");

        when(authService.register(any())).thenReturn(false);

        ResponseEntity<?> response = authController.register(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Ha habido un error"));
    }

    @Test
    public void testLogin_Success() {
        UsuarioLoginDto loginDto = new UsuarioLoginDto("user", "password123");
        when(authService.authenticate("user", "password123")).thenReturn("mocked.token");

        ResponseEntity<?> response = authController.login(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("mocked.token", response.getBody());
    }

    @Test
    public void testLogin_Failure() {
        UsuarioLoginDto loginDto = new UsuarioLoginDto("user", "wrongpassword");
        when(authService.authenticate("user", "wrongpassword")).thenReturn(null);

        ResponseEntity<?> response = authController.login(loginDto);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testConfirmation_Success() {
        Usuario user = new Usuario();
        user.setCorreo("user@example.com");
        user.setTokenVerificacion("token123");
        user.setVerificado(false);

        when(usuarioRepository.findByCorreo("user@example.com")).thenReturn(Optional.of(user));

        ResponseEntity<?> response = authController.confirmation("user@example.com", "token123");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cuenta verificada.", response.getBody());
        assertTrue(user.getVerificado());
    }

    @Test
    public void testConfirmation_TokenInvalid() {
        Usuario user = new Usuario();
        user.setCorreo("user@example.com");
        user.setTokenVerificacion("token123");

        when(usuarioRepository.findByCorreo("user@example.com")).thenReturn(Optional.of(user));

        ResponseEntity<?> response = authController.confirmation("user@example.com", "invalidToken");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Token de verificacion invalido.", response.getBody());
    }

    @Test
    public void testConfirmation_UserNotFound() {
        when(usuarioRepository.findByCorreo("nonexistent@example.com")).thenReturn(Optional.empty());

        ResponseEntity<?> response = authController.confirmation("nonexistent@example.com", "token123");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario no encontrado.", response.getBody());
    }
}
