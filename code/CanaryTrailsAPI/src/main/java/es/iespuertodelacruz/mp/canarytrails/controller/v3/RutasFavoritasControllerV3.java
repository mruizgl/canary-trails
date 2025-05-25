package es.iespuertodelacruz.mp.canarytrails.controller.v3;


import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.rutafavorita.ModificarRutaFavoritaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.mapper.RutaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v3/rutas_favoritas")
@CrossOrigin
public class RutasFavoritasControllerV3 {

    @Autowired
    RutaService rutaService;

    @Autowired
    RutaMapper rutaMapper;


    @GetMapping("/{id}")
    public ResponseEntity<?> findRutasFavoritasByUserId(@PathVariable Integer id) {

        List<Ruta> rutasFavoritasById = rutaService.findRutasFavoritasByUserId(id);
        List<RutaSalidaDto> rutaSalidaDtos = rutasFavoritasById.stream()
                .map(rutaMapper::toDto).toList();

        return ResponseEntity.ok(rutaSalidaDtos);
    }

    @GetMapping("/populares")
    public ResponseEntity<?> findRutasPopularesById() {

        List<Ruta> rutasPopularesById = rutaService.findAllPopulares();
        List<RutaSalidaDto> rutaSalidaDtos = rutasPopularesById.stream()
                .map(rutaMapper::toDto).toList();

        return ResponseEntity.ok(rutaSalidaDtos);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createRutaFavorita(@RequestBody ModificarRutaFavoritaDto dto){
        return ResponseEntity.ok(rutaService.aniadirRutaFavorita(dto.idUsuario(), dto.idRuta()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRutaFavorita(@RequestBody ModificarRutaFavoritaDto dto){
        return ResponseEntity.ok(rutaService.deleteRutaFavorita(dto.idUsuario(), dto.idRuta()));
    }

}
