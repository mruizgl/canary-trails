package es.iespuertodelacruz.mp.canarytrails.controller.v3;

import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.mapper.MunicipioMapper;
import es.iespuertodelacruz.mp.canarytrails.service.MunicipioService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.ZonaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MunicipioControllerV3Test {

    @InjectMocks
    private MunicipioControllerV3 controller;

    @Mock private MunicipioService municipioService;
    @Mock private MunicipioMapper municipioMapper;
    @Mock private ZonaService zonaService;
    @Mock private RutaService rutaService;

    private Municipio municipio;
    private Zona zona;

    @BeforeEach
    void setUp() {
        municipio = new Municipio();
        municipio.setId(1);
        municipio.setNombre("Arona");

        zona = new Zona();
        zona.setId(1);
        zona.setNombre("Sur");
    }

    @Test
    void testGetAll() {
        when(municipioService.findAll()).thenReturn(List.of(municipio));
        when(municipioMapper.toDto(municipio)).thenReturn(new MunicipioSalidaDto(1, "Arona", 100, 28.1f, -16.5f,    null, null));

        ResponseEntity<?> response = controller.getAll();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById_Found() {
        when(municipioService.findById(1)).thenReturn(municipio);
        when(municipioMapper.toDto(municipio)).thenReturn(new MunicipioSalidaDto(1, "Arona", 100, 28.1f, -16.5f, null, null));

        ResponseEntity<?> response = controller.getById(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetById_NotFound() {
        when(municipioService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.getById(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Success() {
        MunicipioEntradaCreateDto dto = new MunicipioEntradaCreateDto("Arona", 100, 28.1f, -16.5f, 1, List.of());

        when(municipioMapper.toEntityCreate(dto)).thenReturn(municipio);
        when(zonaService.findById(1)).thenReturn(zona);
        when(municipioService.save(municipio)).thenReturn(municipio);
        when(municipioMapper.toDto(municipio)).thenReturn(new MunicipioSalidaDto(1, "Arona", 1, 28.1f, -16.5f, null, null));

        ResponseEntity<?> response = controller.create(dto);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCreate_ZonaNotFound() {
        MunicipioEntradaCreateDto dto = new MunicipioEntradaCreateDto("Arona", 100, 28.1f, -16.5f, 99, List.of());
        when(municipioMapper.toEntityCreate(dto)).thenReturn(municipio);
        when(zonaService.findById(99)).thenReturn(null);

        ResponseEntity<?> response = controller.create(dto);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate_SaveException() {
        MunicipioEntradaCreateDto dto = new MunicipioEntradaCreateDto("Arona", 100, 28.1f, -16.5f, 1, List.of());
        when(municipioMapper.toEntityCreate(dto)).thenReturn(municipio);
        when(zonaService.findById(1)).thenReturn(zona);
        when(municipioService.save(municipio)).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.create(dto);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_Success() {
        MunicipioEntradaUpdateDto dto = new MunicipioEntradaUpdateDto(1, "Arona", 100,
                new BigDecimal(15), new BigDecimal(15), 1, List.of());

        when(municipioMapper.toEntityUpdate(dto)).thenReturn(municipio);
        when(zonaService.findById(1)).thenReturn(zona);
        when(municipioService.update(municipio)).thenReturn(true);

        ResponseEntity<?> response = controller.update(dto);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody());
    }

    @Test
    void testUpdate_ZonaNotFound() {
        MunicipioEntradaUpdateDto dto = new MunicipioEntradaUpdateDto(1, "Arona", 100, BigDecimal.valueOf(15), BigDecimal.valueOf(15), 99, List.of());
        when(municipioMapper.toEntityUpdate(dto)).thenReturn(municipio);
        when(zonaService.findById(99)).thenReturn(null);
        when(municipioMapper.toEntityUpdate(dto)).thenReturn(municipio);
        when(zonaService.findById(99)).thenReturn(null);

        ResponseEntity<?> response = controller.update(dto);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_Exception() {
        MunicipioEntradaUpdateDto dto = new MunicipioEntradaUpdateDto(1, "Arona", 100, BigDecimal.valueOf(15), BigDecimal.valueOf(15), 1, List.of(1, 2));
        when(municipioMapper.toEntityUpdate(dto)).thenReturn(municipio);
        when(zonaService.findById(1)).thenReturn(zona);
        when(municipioService.update(municipio)).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.update(dto);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Success() {
        when(municipioService.findById(1)).thenReturn(municipio);
        when(municipioService.deleteById(1)).thenReturn(true);

        ResponseEntity<?> response = controller.delete(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDelete_NotFound() {
        when(municipioService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = controller.delete(1);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDelete_Exception() {
        when(municipioService.findById(1)).thenReturn(municipio);
        when(municipioService.deleteById(1)).thenThrow(new RuntimeException("error"));

        ResponseEntity<?> response = controller.delete(1);
        assertEquals(400, response.getStatusCodeValue());
    }
}
