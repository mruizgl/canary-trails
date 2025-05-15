package es.iespuertodelacruz.mp.canarytrails.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.iespuertodelacruz.mp.canarytrails.config.TestSecurityConfig;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.FaunaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.FaunaService;
import es.iespuertodelacruz.mp.canarytrails.service.FotoManagementService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FaunaControllerAdmin.class)
@Import(TestSecurityConfig.class)
public class FaunaControllerAdminTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    FaunaService faunaService;

    @MockBean
    FaunaMapper faunaMapper;

    @MockBean
    UsuarioService usuarioService;

    @MockBean
    RutaService rutaService;

    @MockBean
    FotoManagementService fotoManagementService;

    private List<Ruta> listaRutas;
    private Usuario usuario;
    private Fauna fauna;
    private FaunaSalidaDto faunaSalidaDto;
    private FaunaEntradaUpdateDto faunaEntradaUpdateDto;

    @BeforeEach
    public void beforeEach() {
        Ruta ruta1 = new Ruta();
        ruta1.setId(1);
        ruta1.setNombre("Ruta de los volcanes");

        Ruta ruta2 = new Ruta();
        ruta2.setId(2);
        ruta2.setNombre("Sendero de los bosques");

        listaRutas = new ArrayList<>();
        listaRutas.add(ruta1);
        listaRutas.add(ruta2);

        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Admin");

        fauna = new Fauna();
        fauna.setId(1);
        fauna.setNombre("Lagarto Gigante");

        faunaSalidaDto = new FaunaSalidaDto(1, "Lagarto Gigante", "Reptil endémico de Canarias",
                true, null, null, null
        );

        faunaEntradaUpdateDto = new FaunaEntradaUpdateDto(1, "Lagarto Gigante", "Reptil",
                true, 1, List.of(1)
        );

    }

    @Test
    public void findAllFaunasTest() throws Exception {
        when(faunaService.findAll()).thenReturn(List.of(fauna));
        when(faunaMapper.toDto(fauna)).thenReturn(faunaSalidaDto);

        mockMvc.perform(get("/api/v1/admin/faunas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void findFaunaByIdTest_NotFound() throws Exception {
        when(faunaService.findById(99)).thenReturn(null);

        mockMvc.perform(get("/api/v1/admin/faunas/99"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void updateFaunaTest() throws Exception {
        when(faunaMapper.toEntityUpdate(faunaEntradaUpdateDto)).thenReturn(fauna);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(listaRutas.get(0));
        when(faunaService.update(fauna)).thenReturn(true);

        mockMvc.perform(put("/api/v1/admin/faunas/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faunaEntradaUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }


    @Test
    public void uploadFileTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "lagarto.jpg", "image/jpeg", "data".getBytes());

        when(fotoManagementService.save(any(), any())).thenReturn("lagarto.jpg");
        when(faunaService.findById(1)).thenReturn(fauna);
        when(faunaService.update(any())).thenReturn(true);

        mockMvc.perform(multipart("/api/v1/admin/faunas/upload/{id}", 1)
                        .file(file)
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().string("lagarto.jpg"));
    }

    @Test
    public void deleteFaunaTest() throws Exception {
        when(faunaService.deleteById(1)).thenReturn(true);

        mockMvc.perform(delete("/api/v1/admin/faunas/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void createFaunaTest() throws Exception {
        List<Integer> idRutas = listaRutas.stream()
                .map(Ruta::getId)
                .toList();

        FaunaEntradaCreateDto dto = new FaunaEntradaCreateDto("Lagarto Gigante", "Reptil endémico",    // descripcion
                true, usuario.getId(), idRutas);


        when(faunaMapper.toEntityCreate(any())).thenReturn(fauna);
        when(faunaService.save(any())).thenReturn(fauna);
        when(faunaMapper.toDto(fauna)).thenReturn(faunaSalidaDto);

        mockMvc.perform(post("/api/v1/admin/faunas/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(fauna.getId()))
                .andExpect(jsonPath("$.nombre").value(fauna.getNombre()));
    }
}
