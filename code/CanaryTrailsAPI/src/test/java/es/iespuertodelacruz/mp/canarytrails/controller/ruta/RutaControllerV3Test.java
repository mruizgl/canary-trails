package es.iespuertodelacruz.mp.canarytrails.controller.v3;

import es.iespuertodelacruz.mp.canarytrails.dto.ruta.CoordenadaEntradaCreate;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.*;
import es.iespuertodelacruz.mp.canarytrails.mapper.RutaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RutaControllerV3Test {

    @InjectMocks
    private RutaControllerV3 controller;

    @Mock private RutaService rutaService;
    @Mock private RutaMapper rutaMapper;
    @Mock private UsuarioService usuarioService;
    @Mock private FloraService floraService;
    @Mock private FaunaService faunaService;
    @Mock private CoordenadaService coordenadaService;
    @Mock private MunicipioService municipioService;
    @Mock private FotoManagementService fotoManagementService;

    private Ruta ruta;
    private Usuario usuario;

    @BeforeEach
    public void setup() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("user");

        ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta");
        ruta.setFotos(new ArrayList<>());
        ruta.setFaunas(new ArrayList<>());
        ruta.setFloras(new ArrayList<>());
        ruta.setCoordenadas(new ArrayList<>());
        ruta.setMunicipios(new ArrayList<>());
        ruta.setUsuario(usuario);
    }

    @Test
    public void testFindAllRutas() {
        when(rutaService.findAll()).thenReturn(List.of(ruta));
        when(rutaMapper.toDto(ruta)).thenReturn(mock(RutaSalidaDto.class));

        ResponseEntity<?> response = controller.findAllRutas();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testFindRutaById_Found() {
        when(rutaService.findById(1)).thenReturn(ruta);
        when(rutaMapper.toDto(ruta)).thenReturn(mock(RutaSalidaDto.class));

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
        RutaEntradaCreateDto dto = new RutaEntradaCreateDto("Ruta", "media", 100L, 1.0f, 1.0f, true, 1, List.of(), List.of(), List.of(), List.of());
        when(rutaMapper.toEntityCreate(dto)).thenReturn(ruta);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.save(any())).thenReturn(ruta);
        when(rutaMapper.toDto(any())).thenReturn(mock(RutaSalidaDto.class));

        ResponseEntity<?> response = controller.createRuta(dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testCreateRuta_UsuarioNotFound() {
        RutaEntradaCreateDto dto = new RutaEntradaCreateDto("Ruta", "media", 100L, 1.0f, 1.0f, true, 99, List.of(), List.of(), List.of(), List.of());
        when(rutaMapper.toEntityCreate(dto)).thenReturn(ruta);
        when(usuarioService.findById(99)).thenReturn(null);

        ResponseEntity<?> response = controller.createRuta(dto);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testCreateRuta_SaveException() {
        RutaEntradaCreateDto dto = new RutaEntradaCreateDto("Ruta", "media", 100L, 1.0f, 1.0f, true, 1, List.of(), List.of(), List.of(), List.of());
        when(rutaMapper.toEntityCreate(dto)).thenReturn(ruta);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.save(any())).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.createRuta(dto);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testUpdateRuta_Success() {
        RutaEntradaUpdateDto dto = new RutaEntradaUpdateDto(1, "Ruta", "media", 100L, 1.0f, 1.0f, true, 1, List.of(), List.of(), List.of(), List.of());
        when(rutaMapper.toEntityUpdate(dto)).thenReturn(ruta);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.update(ruta)).thenReturn(true);

        ResponseEntity<?> response = controller.updateAlumno(dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testUpdateRuta_Exception() {
        RutaEntradaUpdateDto dto = new RutaEntradaUpdateDto(1, "Ruta", "media", 100L, 1.0f, 1.0f, true, 1, List.of(), List.of(), List.of(), List.of());
        when(rutaMapper.toEntityUpdate(dto)).thenReturn(ruta);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.update(ruta)).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.updateAlumno(dto);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testUploadFile_Success() {
        MockMultipartFile file = new MockMultipartFile("file", "foto.jpg", "image/jpeg", "contenido".getBytes());
        when(fotoManagementService.save(file, "ruta")).thenReturn("ruta/foto.jpg");
        when(rutaService.findById(1)).thenReturn(ruta);
        when(rutaService.uploadFotoRuta(any())).thenReturn(true);

        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testUploadFile_Duplicada() {
        ruta.getFotos().add("ruta/foto.jpg");

        MockMultipartFile file = new MockMultipartFile("file", "foto.jpg", "image/jpeg", "contenido".getBytes());
        when(fotoManagementService.save(file, "ruta")).thenReturn("ruta/foto.jpg");
        when(rutaService.findById(1)).thenReturn(ruta);

        ResponseEntity<?> response = controller.uploadFile(1, file);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().toString().contains("existe"));
    }

    @Test
    public void testUploadFile_Exception() {
        MockMultipartFile file = new MockMultipartFile("file", "foto.jpg", "image/jpeg", "contenido".getBytes());
        when(fotoManagementService.save(file, "ruta")).thenThrow(new RuntimeException("error"));

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
    void testUpdateAlumno_AgregaElementosUnicos_NoDuplicados() {
        RutaEntradaUpdateDto dto = new RutaEntradaUpdateDto(
                1, "Ruta Actualizada", "media", 90L, 4.5f, 200f, false, 1,
                List.of(10), List.of(20), List.of(30), List.of(40)
        );

        Usuario usuario = new Usuario(); usuario.setId(1);
        Fauna fauna = new Fauna(); fauna.setId(10);
        Flora flora = new Flora(); flora.setId(20);
        Coordenada coordenada = new Coordenada(); coordenada.setId(30);
        Municipio municipio = new Municipio(); municipio.setId(40);

        Ruta ruta = new Ruta();
        ruta.setFaunas(new ArrayList<>());
        ruta.setFloras(new ArrayList<>());
        ruta.setCoordenadas(new ArrayList<>());
        ruta.setMunicipios(new ArrayList<>());

        when(rutaMapper.toEntityUpdate(dto)).thenReturn(ruta);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(faunaService.findById(10)).thenReturn(fauna);
        when(floraService.findById(20)).thenReturn(flora);
        when(coordenadaService.findById(30)).thenReturn(coordenada);
        when(municipioService.findById(40)).thenReturn(municipio);
        when(rutaService.update(ruta)).thenReturn(true);

        ResponseEntity<?> response = controller.updateAlumno(dto);
        assertEquals(200, response.getStatusCodeValue());

        assertEquals(1, ruta.getFaunas().size());
        assertEquals(1, ruta.getFloras().size());
        assertEquals(1, ruta.getCoordenadas().size());
        assertEquals(1, ruta.getMunicipios().size());
    }
    @Test
    void testCreateRuta_IgnoraElementosDuplicados() {
        Fauna fauna = new Fauna(); fauna.setId(1);
        Flora flora = new Flora(); flora.setId(2);
        Coordenada coordenada = new Coordenada(); coordenada.setId(3);
        Municipio municipio = new Municipio(); municipio.setId(4);
        Usuario usuario = new Usuario(); usuario.setId(1);

        Ruta ruta = new Ruta();
        ruta.setFaunas(new ArrayList<>(List.of(fauna)));
        ruta.setFloras(new ArrayList<>(List.of(flora)));
        ruta.setCoordenadas(new ArrayList<>(List.of(coordenada)));
        ruta.setMunicipios(new ArrayList<>(List.of(municipio)));

        CoordenadaEntradaCreate coordDto = new CoordenadaEntradaCreate(new BigDecimal("33.222222"), new BigDecimal("22.333333"));

        RutaEntradaCreateDto dto = new RutaEntradaCreateDto(
                "Ruta Duplicada", "baja", 60, 2.0f, 150f, false, 1,
                List.of(1), List.of(2), List.of(coordDto), List.of(4)
        );

        when(rutaMapper.toEntityCreate(dto)).thenReturn(ruta);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(faunaService.findById(1)).thenReturn(fauna);
        when(floraService.findById(2)).thenReturn(flora);
        when(coordenadaService.findById(3)).thenReturn(coordenada);
        when(municipioService.findById(4)).thenReturn(municipio);
        when(rutaService.save(ruta)).thenReturn(ruta);
        when(rutaMapper.toDto(ruta)).thenReturn(mock(RutaSalidaDto.class));

        ResponseEntity<?> response = controller.createRuta(dto);

        assertEquals(200, response.getStatusCodeValue());

        // Verifica que no se añadió duplicado
        assertEquals(1, ruta.getFaunas().size());
        assertEquals(1, ruta.getFloras().size());
        assertEquals(1, ruta.getCoordenadas().size());
        assertEquals(1, ruta.getMunicipios().size());
    }
    @Test
    void testCreateRuta_AgregaElementosUnicos_NoDuplicados() {

        CoordenadaEntradaCreate coordDto = new CoordenadaEntradaCreate(new BigDecimal("33.222222"), new BigDecimal("22.333333"));

        // DTO con ids simulados
        RutaEntradaCreateDto dto = new RutaEntradaCreateDto(
                "Ruta test", "media", 120, 5.5f, 300f, false, 1,
                List.of(1, 2), // faunas
                List.of(3),    // floras
                List.of(coordDto),    // coordenadas
                List.of(5)     // municipios
        );

        // Entidades base
        Ruta ruta = new Ruta();
        ruta.setFaunas(new ArrayList<>());
        ruta.setFloras(new ArrayList<>());
        ruta.setCoordenadas(new ArrayList<>());
        ruta.setMunicipios(new ArrayList<>());

        Usuario usuario = new Usuario();
        usuario.setId(1);

        Fauna fauna1 = new Fauna(); fauna1.setId(1);
        Fauna fauna2 = new Fauna(); fauna2.setId(2);
        Flora flora = new Flora(); flora.setId(3);
        Coordenada coordenada = new Coordenada(); coordenada.setId(4);
        Municipio municipio = new Municipio(); municipio.setId(5);

        when(rutaMapper.toEntityCreate(dto)).thenReturn(ruta);
        when(usuarioService.findById(1)).thenReturn(usuario);

        // Faunas
        when(faunaService.findById(1)).thenReturn(fauna1);
        when(faunaService.findById(2)).thenReturn(fauna2);

        // Flora
        when(floraService.findById(3)).thenReturn(flora);

        // Coordenada
        when(coordenadaService.findById(4)).thenReturn(coordenada);

        // Municipio
        when(municipioService.findById(5)).thenReturn(municipio);

        // Simulación del save y mapeo final
        when(rutaService.save(ruta)).thenReturn(ruta);
        when(rutaMapper.toDto(ruta)).thenReturn(mock(RutaSalidaDto.class));

        ResponseEntity<?> response = controller.createRuta(dto);

        assertEquals(200, response.getStatusCodeValue());

        // Validación de que se añadieron elementos una única vez
        assertEquals(2, ruta.getFaunas().size());
        assertTrue(ruta.getFaunas().contains(fauna1));
        assertTrue(ruta.getFaunas().contains(fauna2));

        assertEquals(1, ruta.getFloras().size());
        assertTrue(ruta.getFloras().contains(flora));

        assertEquals(1, ruta.getCoordenadas().size());
        assertTrue(ruta.getCoordenadas().contains(coordenada));

        assertEquals(1, ruta.getMunicipios().size());
        assertTrue(ruta.getMunicipios().contains(municipio));
    }



}
