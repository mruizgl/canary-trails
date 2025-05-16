package es.iespuertodelacruz.mp.canarytrails.controller.v2;


import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.rutafavorita.ModificarRutaFavoritaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.mapper.RutaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/rutas_favoritas")
@CrossOrigin
public class RutasFavoritasControllerV2 {

    @Autowired
    RutaService rutaService;

    @Autowired
    RutaMapper rutaMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> findRutasFavoritasByUserId(@PathVariable Integer id) {
        //TODO: comprobar que el id es el mismo que el del user
        // Find by id, check name

        List<Ruta> rutasFavoritasById = rutaService.findRutasFavoritasByUserId(id);
        List<RutaSalidaDto> rutaSalidaDtos = rutasFavoritasById.stream()
                .map(rutaMapper::toDto).toList();

        return ResponseEntity.ok(rutaSalidaDtos);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createRutaFavorita(@RequestBody ModificarRutaFavoritaDto dto){

        //TODO: comprobar que el id es el mismo que el del user
        // Find by id, check name

        return ResponseEntity.ok(rutaService.aniadirRutaFavorita(dto.idUsuario(), dto.idRuta()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRutaFavorita(@RequestBody ModificarRutaFavoritaDto dto){
        //TODO: comprobar que el id es el mismo que el del user
        // Find by id, check name
        return ResponseEntity.ok(rutaService.deleteRutaFavorita(dto.idUsuario(), dto.idRuta()));
    }

    public boolean esPropietario(Comentario comentario) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return comentario.getUsuario().getNombre().equals(username);
    }

}
