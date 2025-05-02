package es.iespuertodelacruz.mp.canarytrails.controller;

import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaSalidaDtoV2;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioSalidaDtoV2;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.service.FaunaService;
import es.iespuertodelacruz.mp.canarytrails.service.FloraService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/rutas")
@CrossOrigin
public class RutaControllerAdmin {

    @Autowired
    RutaService rutaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    FloraService floraService;

    @Autowired
    FaunaService faunaService;

    //@Autowired
    //ComentarioService comentarioService;

    //@Autowired
    //MunicipioService municipioService;

    @GetMapping
    public ResponseEntity<?> findAllRutas(){

        return ResponseEntity.ok(rutaService.findAll()
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

    @GetMapping("/{id}")
    public ResponseEntity<?> findRutaById(@PathVariable Integer id){

        Ruta ruta = rutaService.findById(id);

        if(ruta == null){
            return ResponseEntity.notFound().build();
        }

        RutaSalidaDtoV2 dto = new RutaSalidaDtoV2(
                ruta.getId(),
                ruta.getNombre(),
                ruta.getDificultad(),
                ruta.getTiempoDuracion(),
                ruta.getDistanciaMetros(),
                ruta.getDesnivel(),
                ruta.getAprobada()
        );

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createRuta(@RequestBody RutaEntradaDto dto){
        //Logger logger = Logger.getLogger("logger");
        //Logger logger = Logger.getLogger(Globals.LOGGER);
        //logger.info("Llamada al find all get /api/usuarios");
        Ruta ruta = new Ruta();
        ruta.setNombre(dto.nombre());
        ruta.setDificultad(dto.dificultad());
        ruta.setAprobada(dto.aprobada());
        ruta.setTiempoDuracion(dto.tiempoDuracion());
        ruta.setDistanciaMetros(dto.distanciaMetros());
        ruta.setDesnivel(dto.desnivel());
        ruta.setAprobada(dto.aprobada());

        /*Usuario usuario = usuarioService.findById(dto.usuario());
        fauna.setUsuario(usuario);*/

        Ruta rutaGuardada = rutaService.save(ruta);

        RutaSalidaDtoV2 dtoSalida = new RutaSalidaDtoV2(
                rutaGuardada.getId(),
                rutaGuardada.getNombre(),
                rutaGuardada.getDificultad(),
                rutaGuardada.getTiempoDuracion(),
                rutaGuardada.getDistanciaMetros(),
                rutaGuardada.getDesnivel(),
                rutaGuardada.getAprobada()
        );

        return ResponseEntity.ok(dtoSalida);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAlumno(@RequestBody RutaEntradaDto dto){
        Ruta ruta = new Ruta();
        ruta.setId(dto.id());
        ruta.setNombre(dto.nombre());
        ruta.setDificultad(dto.dificultad());
        ruta.setAprobada(dto.aprobada());
        ruta.setTiempoDuracion(dto.tiempoDuracion());
        ruta.setDistanciaMetros(dto.distanciaMetros());
        ruta.setDesnivel(dto.desnivel());
        ruta.setAprobada(dto.aprobada());


        return ResponseEntity.ok(rutaService.update(ruta));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRuta(@PathVariable("id") int id){
        //Logger logger = Logger.getLogger("logger");
        //Logger logger = Logger.getLogger(Globals.LOGGER);
        //logger.info("Llamada al find all get /api/alumnos");
        return ResponseEntity.ok(rutaService.deleteById(id));
    }
}
