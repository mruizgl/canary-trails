package es.iespuertodelacruz.mp.canarytrails.controller.v3;


import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaSalidaDtoV2;
import es.iespuertodelacruz.mp.canarytrails.dto.rutafavorita.ModificarRutaFavoritaDto;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v3/rutas_favoritas")
@CrossOrigin
public class RutasFavoritasControllerAdmin {

    @Autowired
    RutaService rutaService;

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findRutasFavoritasByUserId(@PathVariable Integer id) {

        return ResponseEntity.ok(rutaService.findRutasFavoritasByUserId(id)
                .stream()
                .map(ruta -> new RutaSalidaDtoV2(
                                ruta.getId(),
                                ruta.getNombre(),
                                ruta.getDificultad(),
                                ruta.getTiempoDuracion(),
                                ruta.getDistanciaMetros(),
                                ruta.getDesnivel(),
                                ruta.getAprobada()
                        )
                )
                .collect(Collectors.toList())
        );
    }

    @PostMapping("/add")
    public ResponseEntity<?> createRutaFavorita(@RequestBody ModificarRutaFavoritaDto dto){
        return ResponseEntity.ok(rutaService.aniadirRutaFavorita(dto.idUsuario(), dto.idRuta()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRutaFavorita(@RequestBody ModificarRutaFavoritaDto dto){
        return ResponseEntity.ok(rutaService.deleteRutaFavorita(dto.idUsuario(), dto.idRuta()));
    }

}
