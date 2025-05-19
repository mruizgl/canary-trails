package es.iespuertodelacruz.mp.canarytrails.controller.v2;

import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.*;
import es.iespuertodelacruz.mp.canarytrails.mapper.RutaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RutaControllerV2Test {

    @InjectMocks
    private RutaControllerV2 controller;

    @Mock private RutaService rutaService;
    @Mock private RutaMapper rutaMapper;
    @Mock private FloraService floraService;
    @Mock private FaunaService faunaService;
    @Mock private UsuarioService usuarioService;
    @Mock private CoordenadaService coordenadaService;
    @Mock private MunicipioService municipioService;
    @Mock private FotoManagementService fotoManagementService;

    private Ruta ruta;
    private Usuario usuario;
    private List<String> fotos = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ruta = new Ruta();
        ruta.setId(1);
        ruta.setFotos(new ArrayList<>());
        ruta.setFaunas(new ArrayList<>());
        ruta.setFloras(new ArrayList<>());
        ruta.setCoordenadas(new ArrayList<>());
        ruta.setMunicipios(new ArrayList<>());
        ruta.setAprobada(false);

        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("user");
        ruta.setUsuario(usuario);

        fotos.add("foto");
        fotos.add("foto");

        Authentication auth = mock(Authentication.class);
        lenient().when(auth.getName()).thenReturn("user");
        SecurityContext context = mock(SecurityContext.class);
        lenient().when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
    }

    private RutaSalidaDto mockRutaSalidaDto() {
        return new RutaSalidaDto(
                1, "ruta", "media", 0L, 0.0f, 0.0f, false,
                null, null, null, null, null, null, fotos
        );
    }

    @Test
    public void testFindAllRutas() {
        when(rutaService.findAll()).thenReturn(List.of(ruta));
        when(rutaMapper.toDto(any())).thenReturn(mockRutaSalidaDto());

        ResponseEntity<?> response = controller.findAllRutas();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testFindRutaById_Found() {
        when(rutaService.findById(1)).thenReturn(ruta);
        when(rutaMapper.toDto(ruta)).thenReturn(mockRutaSalidaDto());

        ResponseEntity<?> response = controller.findRutaById(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testFindRutaById_NotFound() {
        when(rutaService.findById(1)).thenReturn(null);
        ResponseEntity<?> response = controller.findRutaById(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testCreateRuta_Success() {
        RutaEntradaCreateDto dto = new RutaEntradaCreateDto("ruta", "media", 0L, 0.0f, 0.0f, false, 0, List.of(), List.of(), List.of(), List.of());
        when(rutaMapper.toEntityCreate(dto)).thenReturn(ruta);
        when(usuarioService.findByUserName("user")).thenReturn(usuario);
        when(rutaService.save(any())).thenReturn(ruta);
        when(rutaMapper.toDto(any())).thenReturn(mockRutaSalidaDto());

        ResponseEntity<?> response = controller.createRuta(dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testCreateRuta_SaveFails() {
        RutaEntradaCreateDto dto = new RutaEntradaCreateDto("ruta", "media", 0L, 0.0f, 0.0f, false, 0, List.of(), List.of(), List.of(), List.of());
        when(rutaMapper.toEntityCreate(dto)).thenReturn(ruta);
        when(usuarioService.findByUserName("user")).thenReturn(usuario);
        when(rutaService.save(any())).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.createRuta(dto);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testUploadFile_Success() {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "contenido".getBytes());
        when(fotoManagementService.save(any(), eq("ruta"))).thenReturn("ruta/test.jpg");
        when(rutaService.findById(1)).thenReturn(ruta);
        when(rutaService.update(any())).thenReturn(true);

        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("ruta/test.jpg", response.getBody());
    }

    @Test
    public void testUploadFile_AlreadyExists() {
        ruta.getFotos().add("ruta/test.jpg");
        when(fotoManagementService.save(any(), eq("ruta"))).thenReturn("ruta/test.jpg");
        when(rutaService.findById(1)).thenReturn(ruta);

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "contenido".getBytes());
        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("La foto ya existe"));
    }

    @Test
    public void testUploadFile_RutaNotFound() {
        when(fotoManagementService.save(any(), eq("ruta"))).thenReturn("ruta/test.jpg");
        when(rutaService.findById(1)).thenReturn(null);

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "contenido".getBytes());
        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testUploadFile_NotOwnerOrApproved() {
        ruta.setAprobada(true);
        when(fotoManagementService.save(any(), eq("ruta"))).thenReturn("ruta/test.jpg");
        when(rutaService.findById(1)).thenReturn(ruta);

        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "contenido".getBytes());
        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(403, response.getStatusCodeValue());
    }

    @Test
    public void testUploadFile_Exception() {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "contenido".getBytes());
        when(fotoManagementService.save(any(), eq("ruta"))).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteRuta_Success() {
        when(rutaService.findById(1)).thenReturn(ruta);
        when(rutaService.deleteById(1)).thenReturn(true);

        ResponseEntity<?> response = controller.deleteRuta(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteRuta_NotFound() {
        when(rutaService.findById(1)).thenReturn(null);
        ResponseEntity<?> response = controller.deleteRuta(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteRuta_NotOwnerOrApproved() {
        ruta.setAprobada(true);
        when(rutaService.findById(1)).thenReturn(ruta);

        ResponseEntity<?> response = controller.deleteRuta(1);
        assertEquals(403, response.getStatusCodeValue());
    }
}
