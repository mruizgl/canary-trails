package es.iespuertodelacruz.mp.canarytrails.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", "mySecretKey123456"); // clave secreta de psrueba
        ReflectionTestUtils.setField(jwtService, "expiration", 60000L); // 1 minuto le pongo para probar
    }

    @Test
    public void generateTokenAndValidateClaimsTest() {
        String username = "testUser";
        String role = "ROLE_USER";

        String token = jwtService.generateToken(username, role);
        assertNotNull(token);

        Map<String, String> claims = jwtService.validateAndGetClaims(token);

        assertEquals(username, claims.get("username"));
        assertEquals(role, claims.get("role"));
    }

    @Test
    public void validateTokenConClaveIncorrecta_LanzaExcepcion() {
        String username = "user";
        String role = "ROLE_ADMIN";

        // Crear token con clave válida
        String token = jwtService.generateToken(username, role);

        // Crear otro servicio con clave incorrecta
        JwtService servicioInvalido = new JwtService();
        ReflectionTestUtils.setField(servicioInvalido, "secret", "claveInvalida");
        ReflectionTestUtils.setField(servicioInvalido, "expiration", 60000L);

        assertThrows(JWTVerificationException.class, () -> {
            servicioInvalido.validateAndGetClaims(token);
        });
    }

    @Test
    public void validateTokenExpirado_LanzaExcepcion() throws InterruptedException {
        // Crear servicio con expiración muy corta
        JwtService servicioExpira = new JwtService();
        ReflectionTestUtils.setField(servicioExpira, "secret", "mySecretKey123456");
        ReflectionTestUtils.setField(servicioExpira, "expiration", 1L); // 1 ms

        String token = servicioExpira.generateToken("expirable", "ROLE_TEST");

        // Esperar un poco para que expire
        Thread.sleep(5);

        assertThrows(JWTVerificationException.class, () -> {
            servicioExpira.validateAndGetClaims(token);
        });
    }
}
