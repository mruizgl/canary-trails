package es.iespuertodelacruz.mp.canarytrails.controller.v2;

import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.ComentarioMapper;
import es.iespuertodelacruz.mp.canarytrails.service.ComentarioService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Los administradores pueden ver, crear, editar y eliminar todos los comentarios.
 * @author Melissa R.G. y Pedro M.E.
 */
@RestController
@RequestMapping("/api/v2/comentarios")
@CrossOrigin(origins = "*")
public class ComentarioControllerV2 {

    @Autowired
    ComentarioService comentarioService;

    @Autowired
    ComentarioMapper comentarioMapper;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RutaService rutaService;

    /**
     * Endpoint que devuelve un comentario segun la id introducida
     * @param id del comentario que se quiere obtener
     * @return comentario cuya id coincide con la introducida
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Comentario comentario = comentarioService.findById(id);

        if(comentario == null){
            return ResponseEntity.notFound().build();
        }

        ComentarioSalidaDto dto = comentarioMapper.toDto(comentario);

        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint para crear un comentario
     * @param dto con los datos del comentario a crear
     * @return el comentario creado
     */
    @PostMapping("/add")
    public ResponseEntity<?> crearComentario(@RequestBody ComentarioEntradaCreateDto dto) {

        Comentario comentario = comentarioMapper.toEntityCreate(dto);
        Usuario usuario = usuarioService.findById(dto.usuario());

        if(usuario == null){
            return ResponseEntity.notFound().build();
        }

        Ruta ruta = rutaService.findById(dto.ruta());

        if(ruta == null){
            return ResponseEntity.notFound().build();
        }

        comentario.setUsuario(usuario);
        comentario.setRuta(ruta);

        try {
            comentario = comentarioService.save(comentario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(comentarioMapper.toDto(comentario));
    }

    /**
     * Endpoint para actualizar un comentario existente
     * @param dto con los datos a actualizar
     * @return true si se ha actualizado correctamente o false si no
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateComentario(@RequestBody ComentarioEntradaUpdateDto dto) {

        //TODO: Comprobar si es el creador
        Comentario comentario = comentarioMapper.toEntityUpdate(dto);

        boolean actualizado;

        try {
            actualizado = comentarioService.update(comentario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(actualizado);
    }

    /**
     * Endpoint que elimina un comentario a partir de una id
     * @param id del comentario a eliminar
     * @return true si se ha borrado correctamente o false si no
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarComentario(@PathVariable Integer id) {

        //TODO: comprobar si es el creador

        if(comentarioService.findById(id) == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(comentarioService.deleteById(id));
    }

    public boolean esPropietario(Comentario comentario) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return comentario.getUsuario().getNombre().equals(username);
    }
}
