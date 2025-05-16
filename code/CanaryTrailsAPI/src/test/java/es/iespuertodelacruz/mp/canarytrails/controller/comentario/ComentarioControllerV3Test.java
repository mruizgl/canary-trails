package es.iespuertodelacruz.mp.canarytrails.controller.comentario;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.iespuertodelacruz.mp.canarytrails.config.TestSecurityConfig;
import es.iespuertodelacruz.mp.canarytrails.controller.v3.ComentarioControllerV3;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.*;
import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.ComentarioMapper;
import es.iespuertodelacruz.mp.canarytrails.mapper.RelacionesMapper;
import es.iespuertodelacruz.mp.canarytrails.service.ComentarioService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ComentarioControllerV3.class)
@Import(TestSecurityConfig.class)
public class ComentarioControllerV3Test {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ComentarioService comentarioService;

    @MockBean
    ComentarioMapper comentarioMapper;

    @MockBean
    UsuarioService usuarioService;

    @MockBean
    RutaService rutaService;

    @MockBean
    RelacionesMapper relacionesMapper;

    private Comentario comentario;
    private ComentarioSalidaDto comentarioDto;
    private Usuario usuario;
    private Ruta ruta;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Juan");

        ruta = new Ruta();
        ruta.setId(1);
        ruta.setNombre("Ruta");

        RutaSalidaComentarioDto rutaSalidaComentarioDto = relacionesMapper.toSalidaComentario(ruta);
        UsuarioSalidaComentarioDto usuarioSalidaComentarioDto = relacionesMapper.toSalidaComentario(usuario);

        comentario = new Comentario();
        comentario.setId(1);
        comentario.setTitulo("Título");
        comentario.setDescripcion("Desc");
        comentario.setUsuario(usuario);
        comentario.setRuta(ruta);

        comentarioDto = new ComentarioSalidaDto(1, "Título", "Desc", usuarioSalidaComentarioDto,
                rutaSalidaComentarioDto);
    }

    @Test
    void getAllTest() throws Exception {
        when(comentarioService.findAll()).thenReturn(List.of(comentario));
        when(comentarioMapper.toDto(comentario)).thenReturn(comentarioDto);

        mockMvc.perform(get("/api/v3/comentarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void getByIdTest_found() throws Exception {
        when(comentarioService.findById(1)).thenReturn(comentario);
        when(comentarioMapper.toDto(comentario)).thenReturn(comentarioDto);

        mockMvc.perform(get("/api/v3/comentarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getByIdTest_notFound() throws Exception {
        when(comentarioService.findById(1)).thenReturn(null);

        mockMvc.perform(get("/api/v3/comentarios/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearComentarioTest_ok() throws Exception {
        ComentarioEntradaCreateDto dto = new ComentarioEntradaCreateDto("Título", "Desc", 1, 1);

        when(comentarioMapper.toEntityCreate(dto)).thenReturn(comentario);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(ruta);
        when(comentarioService.save(any())).thenReturn(comentario);
        when(comentarioMapper.toDto(any())).thenReturn(comentarioDto);

        mockMvc.perform(post("/api/v3/comentarios/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void crearComentarioTest_usuarioNotFound() throws Exception {
        ComentarioEntradaCreateDto dto = new ComentarioEntradaCreateDto("Título", "Desc", 1, 1);
        when(usuarioService.findById(1)).thenReturn(null);

        mockMvc.perform(post("/api/v3/comentarios/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    void editarComentarioTest_ok() throws Exception {
        ComentarioEntradaUpdateDto dto = new ComentarioEntradaUpdateDto(1, "Nuevo", "Desc", 1, 1);
        when(comentarioMapper.toEntityUpdate(dto)).thenReturn(comentario);
        when(usuarioService.findById(1)).thenReturn(usuario);
        when(rutaService.findById(1)).thenReturn(ruta);
        when(comentarioService.update(any())).thenReturn(true);

        mockMvc.perform(put("/api/v3/comentarios/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void eliminarComentarioTest_ok() throws Exception {
        when(comentarioService.findById(1)).thenReturn(comentario);
        when(comentarioService.deleteById(1)).thenReturn(true);

        mockMvc.perform(delete("/api/v3/comentarios/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void eliminarComentarioTest_notFound() throws Exception {
        when(comentarioService.findById(1)).thenReturn(null);

        mockMvc.perform(delete("/api/v3/comentarios/delete/1"))
                .andExpect(status().isNotFound());
    }
}
