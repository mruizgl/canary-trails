package es.iespuertodelacruz.mp.canarytrails.controller.admin;

import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.service.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Los administradores pueden ver, crear, editar y eliminar todos los comentarios.
 * @author Melissa R.G. y Pedro M.E.
 */
@RestController
@RequestMapping("/api/v1/admin/comentarios")
public class ComentarioControllerAdmin {

    @Autowired
    private ComentarioService comentarioService;

    @GetMapping
    public List<ComentarioSalidaDto> getAll() {
        return comentarioService.findAllSalida();
    }

    @GetMapping("/{id}")
    public ComentarioSalidaDto getById(@PathVariable Integer id) {
        return comentarioService.findSalidaById(id);
    }

    @PostMapping
    public ComentarioSalidaDto crearComentario(@RequestBody ComentarioEntradaDto dto) {
        return comentarioService.saveEntrada(dto);
    }

    @PutMapping("/{id}")
    public ComentarioSalidaDto editarComentario(@PathVariable Integer id, @RequestBody ComentarioEntradaDto dto) {
        return comentarioService.update(id, dto, null, true);
    }

    @DeleteMapping("/{id}")
    public void eliminarComentario(@PathVariable Integer id) {
        comentarioService.deleteById(id);
    }
}
