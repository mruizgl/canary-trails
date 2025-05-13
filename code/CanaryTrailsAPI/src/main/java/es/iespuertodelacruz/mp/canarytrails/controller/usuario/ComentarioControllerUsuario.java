package es.iespuertodelacruz.mp.canarytrails.controller.usuario;

import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Los usuarios pueden ver, crear, editar y eliminar sus propios comentarios.
 * @author Melissa R.G. y Pedro M.E.
 */
@RestController
@RequestMapping("/api/v1/user/comentarios")
public class ComentarioControllerUsuario {

    @Autowired
    private ComentarioService comentarioService;

    // TODO: Sustituir por el ID real del usuario autenticado , lo hago asi porque aun no tenemos securizado
    private Integer getUsuarioActualId() {
        return 1;
    }

    @GetMapping
    public List<ComentarioSalidaDto> getMisComentarios() {
        return comentarioService.findByUsuarioId(getUsuarioActualId());
    }

    @PostMapping
    public ComentarioSalidaDto crearComentario(@RequestBody ComentarioEntradaCreateDto dto) {
        dto = new ComentarioEntradaCreateDto(
                dto.titulo(),
                dto.descripcion(),
                getUsuarioActualId(),
                dto.rutaId()
        );
        return comentarioService.saveEntrada(dto);
    }

    @PutMapping("/{id}")
    public ComentarioSalidaDto editarComentario(@PathVariable Integer id, @RequestBody ComentarioEntradaCreateDto dto) {
        return comentarioService.update(id, dto, getUsuarioActualId(), false);
    }

    @DeleteMapping("/{id}")
    public void eliminarComentario(@PathVariable Integer id) {
        comentarioService.update(id, new ComentarioEntradaCreateDto("", "", getUsuarioActualId(), 0), getUsuarioActualId(), false);
        comentarioService.deleteById(id);
    }
}
