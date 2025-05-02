package es.iespuertodelacruz.mp.canarytrails.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.iespuertodelacruz.mp.canarytrails.config.TestSecurityConfig;
import es.iespuertodelacruz.mp.canarytrails.dto.CoordenadaDTO;
import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import es.iespuertodelacruz.mp.canarytrails.mapper.CoordenadaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.CoordenadaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestSecurityConfig.class)
@WebMvcTest(CoordenadaController.class)
class CoordenadaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoordenadaService coordenadaService;

    @MockBean
    private CoordenadaMapper coordenadaMapper;

    private Coordenada coordenada;
    private CoordenadaDTO coordenadaDTO;

    @BeforeEach
    void setup() {
        coordenada = new Coordenada();
        coordenada.setId(1);
        coordenada.setLatitud(new BigDecimal("28.123456"));
        coordenada.setLongitud(new BigDecimal("-16.654321"));

        coordenadaDTO = new CoordenadaDTO(1, new BigDecimal("28.123456"), new BigDecimal("-16.654321"));
    }

    @Test
    void testGetAll() throws Exception {
        when(coordenadaService.findAll()).thenReturn(Collections.singletonList(coordenada));
        when(coordenadaMapper.toDTOList(any())).thenReturn(Collections.singletonList(coordenadaDTO));

        mockMvc.perform(get("/coordenadas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].latitud").value("28.123456"));
    }

    @Test
    void testGetById() throws Exception {
        when(coordenadaService.findById(1)).thenReturn(Optional.of(coordenada));
        when(coordenadaMapper.toDTO(coordenada)).thenReturn(coordenadaDTO);

        mockMvc.perform(get("/coordenadas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.longitud").value("-16.654321"));
    }

    @Test
    void testCreate() throws Exception {
        when(coordenadaMapper.toEntity(any())).thenReturn(coordenada);
        when(coordenadaService.save(any())).thenReturn(coordenada);
        when(coordenadaMapper.toDTO(any())).thenReturn(coordenadaDTO);

        mockMvc.perform(post("/coordenadas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(coordenadaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.latitud").value("28.123456"));
    }

    @Test
    void testUpdate() throws Exception {
        when(coordenadaMapper.toEntity(any())).thenReturn(coordenada);
        when(coordenadaService.save(any())).thenReturn(coordenada);
        when(coordenadaMapper.toDTO(any())).thenReturn(coordenadaDTO);

        mockMvc.perform(put("/coordenadas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(coordenadaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete("/coordenadas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateBatch() throws Exception {
        List<CoordenadaDTO> dtoList = Collections.singletonList(coordenadaDTO);
        List<Coordenada> entityList = Collections.singletonList(coordenada);

        when(coordenadaMapper.toEntityList(dtoList)).thenReturn(entityList);
        when(coordenadaService.saveAll(entityList)).thenReturn(entityList);
        when(coordenadaMapper.toDTOList(entityList)).thenReturn(dtoList);

        mockMvc.perform(post("/coordenadas/lote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(dtoList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].latitud").value("28.123456"))
                .andExpect(jsonPath("$[0].longitud").value("-16.654321"));
    }
}
