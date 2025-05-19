package es.iespuertodelacruz.mp.canarytrails.security;

import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.repository.UsuarioRepository;
import es.iespuertodelacruz.mp.canarytrails.service.MailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private MailService mailService;

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("testUser");
        usuario.setCorreo("test@correo.com");
        usuario.setPassword("12345678");
        usuario.setRol("ROLE_USER");
        usuario.setVerificado(false);
        usuario.setFechaCreacion(new Date());
        usuario.setTokenVerificacion(UUID.randomUUID().toString());
        usuario.setFoto("src/main/resources/uploads/usuario/default.png");
    }

    @Test
    public void registerUsuarioValidoTest() {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any())).thenReturn(usuario);

        boolean resultado = authService.register(usuario);

        assertTrue(resultado);
        verify(mailService, times(1)).send(any(), any(), contains("http://localhost:8080/api/v1/auth/confirmacion/"));
    }

    @Test
    public void registerUsuarioFallaSaveTest() {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(usuarioRepository.save(any())).thenReturn(null);

        boolean resultado = authService.register(usuario);

        assertFalse(resultado);
        verify(mailService, never()).send(any(), any(), any());
    }

    @Test
    public void authenticateUsuarioValidoYVerificadoTest() {
        usuario.setVerificado(true);
        when(usuarioRepository.findByNombre("testUser")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("12345678", usuario.getPassword())).thenReturn(true);
        when(jwtService.generateToken("testUser", "ROLE_USER")).thenReturn("mocked.token");

        String token = authService.authenticate("testUser", "12345678");

        assertEquals("mocked.token", token);
    }

    @Test
    public void authenticateUsuarioNoVerificadoTest() {
        usuario.setVerificado(false);
        when(usuarioRepository.findByNombre("testUser")).thenReturn(Optional.of(usuario));

        String token = authService.authenticate("testUser", "12345678");

        assertNull(token);
    }

    @Test
    public void authenticateUsuarioPasswordIncorrectaTest() {
        usuario.setVerificado(true);
        when(usuarioRepository.findByNombre("testUser")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("wrongPassword", usuario.getPassword())).thenReturn(false);

        String token = authService.authenticate("testUser", "wrongPassword");

        assertNull(token);
    }

    @Test
    public void authenticateUsuarioNoExisteTest() {
        when(usuarioRepository.findByNombre("desconocido")).thenReturn(Optional.empty());

        String token = authService.authenticate("desconocido", "12345678");

        assertNull(token);
    }
}
