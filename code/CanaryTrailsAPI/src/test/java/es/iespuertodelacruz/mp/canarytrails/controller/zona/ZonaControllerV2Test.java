package es.iespuertodelacruz.mp.canarytrails.controller.v2;

import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.mapper.ZonaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.ZonaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ZonaControllerV2Test {

    @InjectMocks
    private ZonaControllerV2 controller;

    @Mock
    private ZonaService zonaService;

    @Mock
    private ZonaMapper zonaMapper;

    private Zona zona;

    @BeforeEach
    public void setUp() {
        zona = new Zona();
        zona.setId(1);
        zona.setNombre("Norte");
    }

    @Test
    public void testGetAll_ReturnsZonas() {
        when(zonaService.findAll()).thenReturn(List.of(zona));
        when(zonaMapper.toDTOList(List.of(zona))).thenReturn(List.of(new ZonaSalidaDto(1, "Norte", null)));

        ResponseEntity<?> response = controller.getAll();
        assertEquals(200, response.getStatusCodeValue());

        List<ZonaSalidaDto> result = (List<ZonaSalidaDto>) response.getBody();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Norte", result.get(0).nombre());
    }

    @Test
    public void testGetById_Found() {
        when(zonaService.findById(1)).thenReturn(zona);
        when(zonaMapper.toDTO(zona)).thenReturn(new ZonaSalidaDto(1, "Norte", null));

        ResponseEntity<?> response = controller.getById(1);
        assertEquals(200, response.getStatusCodeValue());

        ZonaSalidaDto dto = (ZonaSalidaDto) response.getBody();
        assertNotNull(dto);
        assertEquals("Norte", dto.nombre());
    }

    @Test
    public void testGetById_NotFound() {
        when(zonaService.findById(999)).thenReturn(null);

        ResponseEntity<?> response = controller.getById(999);
        assertEquals(404, response.getStatusCodeValue());
    }
}
