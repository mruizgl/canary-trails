package es.iespuertodelacruz.mp.canarytrails.controller;

import es.iespuertodelacruz.mp.canarytrails.dto.fauna.*;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioSalidaDtoV2;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.service.FaunaService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/faunas")
@CrossOrigin
public class FaunaControllerAdmin {

    
    @Autowired
    FaunaService faunaService;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    RutaService rutaService;

    /**
     * Endpoint para obtener todas las faunas
     * @return una lista de todas las faunas con sus respectivas relaciones
     */
    @GetMapping
    public ResponseEntity<?> findAllFaunas(){
        //System.out.println("holaaaaaa estoy aqui");

        return ResponseEntity.ok(faunaService.findAll()
                        .stream()
                        .map(fauna -> new FaunaSalidaDto(
                                        fauna.getId(),
                                        fauna.getNombre(),
                                        fauna.getDescripcion(),
                                        fauna.getAprobada(),
                                        new UsuarioSalidaFaunaDto(
                                                fauna.getUsuario().getId(),
                                                fauna.getUsuario().getNombre(),
                                                fauna.getUsuario().getApellidos(),
                                                fauna.getUsuario().getCorreo(),
                                                fauna.getUsuario().getPassword(),
                                                fauna.getUsuario().getVerificado(),
                                                fauna.getUsuario().getRol()
                                        ),
                                        fauna.getRutas()
                                                .stream()
                                                .map(ruta -> new RutaSalidaFaunaDto(
                                                        ruta.getId(),
                                                        ruta.getNombre(),
                                                        ruta.getDificultad(),
                                                        ruta.getTiempoDuracion(),
                                                        ruta.getDistanciaMetros(),
                                                        ruta.getDesnivel(),
                                                        ruta.getAprobada()
                                                )
                                        ).toList()
                                )
                        )
                .collect(Collectors.toList())
        );
    }

    /**
     * Endpoint para obtener una fauna a partir de su id
     * @param id de la fauna que se quiere obtener
     * @return la fauna cuyo id coincide con el introducido
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findFaunaById(@PathVariable Integer id){

        Fauna fauna = faunaService.findById(id);

        if(fauna == null){
            return ResponseEntity.notFound().build();
        }

        FaunaSalidaDto dto = new FaunaSalidaDto(
                        fauna.getId(),
                        fauna.getNombre(),
                        fauna.getDescripcion(),
                        fauna.getAprobada(),
                        new UsuarioSalidaFaunaDto(
                                fauna.getUsuario().getId(),
                                fauna.getUsuario().getNombre(),
                                fauna.getUsuario().getApellidos(),
                                fauna.getUsuario().getCorreo(),
                                fauna.getUsuario().getPassword(),
                                fauna.getUsuario().getVerificado(),
                                fauna.getUsuario().getRol()
                        ),
                        fauna.getRutas()
                                .stream()
                                .map(ruta -> new RutaSalidaFaunaDto(
                                                ruta.getId(),
                                                ruta.getNombre(),
                                                ruta.getDificultad(),
                                                ruta.getTiempoDuracion(),
                                                ruta.getDistanciaMetros(),
                                                ruta.getDesnivel(),
                                                ruta.getAprobada()
                                        )
                                ).toList()
        );

        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint para añadir una fauna a la bbdd
     * @param dto con los datos que se deben introducir para crear la fauna
     * @return un JSON de la fauna creada
     */
    @PostMapping("/add")
    public ResponseEntity<?> createFauna(@RequestBody FaunaEntradaCreateDto dto){
        //Logger logger = Logger.getLogger("logger");
        //Logger logger = Logger.getLogger(Globals.LOGGER);
        //logger.info("Llamada al find all get /api/usuarios");
        Fauna fauna = new Fauna();
        fauna.setNombre(dto.nombre());
        fauna.setDescripcion(dto.descripcion());
        fauna.setAprobada(dto.aprobada());

        Usuario usuario = usuarioService.findById(dto.usuario());
        fauna.setUsuario(usuario);

        for( int id : dto.rutas()){
            Ruta ruta = rutaService.findById(id);
            if(ruta != null){
                fauna.getRutas().add(ruta);
            }
        }

        Fauna faunaGuardada = faunaService.save(fauna);

        FaunaSalidaDto dtoSalida = new FaunaSalidaDto(
                faunaGuardada.getId(),
                faunaGuardada.getNombre(),
                faunaGuardada.getDescripcion(),
                faunaGuardada.getAprobada(),
                new UsuarioSalidaFaunaDto(
                        faunaGuardada.getUsuario().getId(),
                        faunaGuardada.getUsuario().getNombre(),
                        faunaGuardada.getUsuario().getApellidos(),
                        faunaGuardada.getUsuario().getCorreo(),
                        faunaGuardada.getUsuario().getPassword(),
                        faunaGuardada.getUsuario().getVerificado(),
                        faunaGuardada.getUsuario().getRol()
                ),
                faunaGuardada.getRutas()
                        .stream()
                        .map(ruta -> new RutaSalidaFaunaDto(
                                        ruta.getId(),
                                        ruta.getNombre(),
                                        ruta.getDificultad(),
                                        ruta.getTiempoDuracion(),
                                        ruta.getDistanciaMetros(),
                                        ruta.getDesnivel(),
                                        ruta.getAprobada()
                                )
                        ).toList()
        );

        return ResponseEntity.ok(dtoSalida);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAlumno(@RequestBody FaunaEntradaUpdateDto dto){
        //Logger logger = Logger.getLogger("logger");
        //Logger logger = Logger.getLogger(Globals.LOGGER);
        //logger.info("Llamada al find all get /api/alumnos");
        Fauna fauna = new Fauna();
        fauna.setId(dto.id());
        fauna.setNombre(dto.nombre());
        fauna.setDescripcion(dto.descripcion());
        fauna.setAprobada(dto.aprobada());

        Usuario usuario = usuarioService.findById(dto.usuario());
        fauna.setUsuario(usuario);

        if(dto.rutas() != null){
            List<Ruta> nuevasRutas = new ArrayList<>();
            for( int id : dto.rutas()){
                Ruta ruta = rutaService.findById(id);
                if(ruta != null){
                    nuevasRutas.add(ruta);
                }
            }
            fauna.setRutas(nuevasRutas);
        }

        return ResponseEntity.ok(faunaService.update(fauna));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFauna(@PathVariable("id") int id){
        //Logger logger = Logger.getLogger("logger");
        //Logger logger = Logger.getLogger(Globals.LOGGER);
        //logger.info("Llamada al find all get /api/alumnos");
        return ResponseEntity.ok(faunaService.deleteById(id));
    }
}
