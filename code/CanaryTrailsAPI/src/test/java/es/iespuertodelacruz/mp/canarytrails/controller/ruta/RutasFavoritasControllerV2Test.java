package es.iespuertodelacruz.mp.canarytrails.controller.v2;

import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.rutafavorita.ModificarRutaFavoritaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.RutaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RutasFavoritasControllerV2Test {

    @InjectMocks
    private RutasFavoritasControllerV2 controller;

    @Mock private RutaService rutaService;
    @Mock private RutaMapper rutaMapper;
    @Mock private UsuarioService usuarioService;

    private Usuario usuario;
    private Ruta ruta;
    private List<String> fotos = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("user");

        ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta 1");

        fotos.add("foto");
        fotos.add("foto");

        Authentication auth = mock(Authentication.class);
        lenient().when(auth.getName()).thenReturn("user");

        SecurityContext context = mock(SecurityContext.class);
        lenient().when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);
    }

    @Test
    public void testFindRutasFavoritasByUserId_ReturnsList() {
        when(rutaService.findRutasFavoritasByUserId(1)).thenReturn(List.of(ruta));
        when(rutaMapper.toDto(ruta)).thenReturn(new RutaSalidaDto(
                1, "Ruta 1", "media", 3600L, 12.5f, 200f,
                true, null, null, null, null, null, null, fotos
        ));

        ResponseEntity<?> response = controller.findRutasFavoritasByUserId(1);
        assertEquals(200, response.getStatusCodeValue());
        List<RutaSalidaDto> result = (List<RutaSalidaDto>) response.getBody();
        assertEquals(1, result.size());
        assertEquals("Ruta 1", result.get(0).nombre());
    }

    @Test
    public void testCreateRutaFavorita_Success() {
        ModificarRutaFavoritaDto dto = new ModificarRutaFavoritaDto(1, 1);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.aniadirRutaFavorita(1, 1)).thenReturn(true);

        ResponseEntity<?> response = controller.createRutaFavorita(dto);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    public void testCreateRutaFavorita_Forbidden() {
        Usuario otro = new Usuario();
        otro.setNombre("otro");
        ModificarRutaFavoritaDto dto = new ModificarRutaFavoritaDto(1, 1);

        when(usuarioService.findById(1)).thenReturn(otro);

        ResponseEntity<?> response = controller.createRutaFavorita(dto);
        assertEquals(403, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("añadir rutas favoritas"));
    }

    @Test
    public void testDeleteRutaFavorita_Success() {
        ModificarRutaFavoritaDto dto = new ModificarRutaFavoritaDto(1, 1);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.deleteRutaFavorita(1, 1)).thenReturn(true);

        ResponseEntity<?> response = controller.deleteRutaFavorita(dto);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    public void testDeleteRutaFavorita_Forbidden() {
        Usuario otro = new Usuario();
        otro.setNombre("otro");
        ModificarRutaFavoritaDto dto = new ModificarRutaFavoritaDto(1, 1);

        when(usuarioService.findById(1)).thenReturn(otro);

        ResponseEntity<?> response = controller.deleteRutaFavorita(dto);
        assertEquals(403, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("eliminar rutas favoritas"));
    }
}
