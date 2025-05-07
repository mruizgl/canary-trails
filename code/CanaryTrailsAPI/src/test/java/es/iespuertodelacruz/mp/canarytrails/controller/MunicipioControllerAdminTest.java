package es.iespuertodelacruz.mp.canarytrails.controller;

import es.iespuertodelacruz.mp.canarytrails.config.TestSecurityConfig;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.service.MunicipioService;
import es.iespuertodelacruz.mp.canarytrails.mapper.MunicipioMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(TestSecurityConfig.class)
@WebMvcTest(MunicipioControllerAdmin.class)
public class MunicipioControllerAdminTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MunicipioService municipioService;

    @MockBean
    private MunicipioMapper municipioMapper;

    @Test
    void testGetAllMunicipios() throws Exception {
        when(municipioService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v1/admin/municipios")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
