package es.iespuertodelacruz.mp.canarytrails.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import es.iespuertodelacruz.mp.canarytrails.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JwtFilterTest {

    @InjectMocks
    private JwtFilter jwtFilter;

    @Mock
    private JwtService jwtService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        jwtFilter = new JwtFilter();
        ReflectionTestUtils.setField(jwtFilter, "jwtTokenManager", jwtService);
        ReflectionTestUtils.setField(jwtFilter, "usuarioRepository", usuarioRepository);
    }


    @Test
    public void testRutaPublica_noFiltra() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/swagger-ui/index.html");
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    public void testTokenValido_ContinuaCadena() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/protegido");
        request.addHeader("Authorization", "Bearer valid.token.here");

        MockHttpServletResponse response = new MockHttpServletResponse();

        Map<String, String> claims = new HashMap<>();
        claims.put("username", "usuario");
        claims.put("role", "ROLE_USER");

        when(jwtService.validateAndGetClaims("valid.token.here")).thenReturn(claims);

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("usuario", SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    public void testTokenInvalido_Retorna401() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/protegido");
        request.addHeader("Authorization", "Bearer invalid.token");

        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtService.validateAndGetClaims("invalid.token")).thenThrow(new JWTVerificationException("Token inválido"));

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, never()).doFilter(any(), any());
        assertEquals(HttpServletResponse.SC_UNAUTHORIZED, response.getStatus());
    }

    @Test
    public void testSinHeaderAuthorization_NoAutentica() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/protegido");

        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain, never()).doFilter(any(), any());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}
