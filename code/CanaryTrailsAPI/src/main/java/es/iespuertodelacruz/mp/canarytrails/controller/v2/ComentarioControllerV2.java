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
import org.springframework.http.HttpStatus;
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

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.findByUserName(username);
        comentario.setUsuario(usuario);

        if(usuario == null){
            return ResponseEntity.notFound().build();
        }

        Ruta ruta = rutaService.findById(dto.ruta());

        if(ruta == null){
            return ResponseEntity.notFound().build();
        }

        comentario.setRuta(ruta);

        try {
            comentario = comentarioService.save(comentario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(comentarioMapper.toDto(comentario));
    }

    /**
     * Endpoint que elimina un comentario a partir de una id
     * @param id del comentario a eliminar
     * @return true si se ha borrado correctamente o false si no
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> eliminarComentario(@PathVariable Integer id) {

        if(comentarioService.findById(id) == null){
            return ResponseEntity.notFound().build();
        }

        if(!esPropietario(comentarioService.findById(id))){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es el creador del comentario");
        }

        return ResponseEntity.ok(comentarioService.deleteById(id));
    }

    public boolean esPropietario(Comentario comentario) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return comentario.getUsuario().getNombre().equals(username);
    }
}
