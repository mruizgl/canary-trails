package es.iespuertodelacruz.mp.canarytrails.controller;

import es.iespuertodelacruz.mp.canarytrails.dto.FaunaEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.FaunaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.UsuarioRegisterDto;
import es.iespuertodelacruz.mp.canarytrails.dto.UsuarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.service.FaunaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/faunas")
@CrossOrigin
public class FaunaControllerAdmin {
    /**


    @Autowired
    FaunaService faunaService;
    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> findAllFaunas(){
        System.out.println("holaaaaaa estoy aqui");

        return ResponseEntity.ok(faunaService.findAll()
                        .stream()
                        .map(fauna -> new FaunaSalidaDto(
                                        fauna.getId(),
                                        fauna.getNombre(),
                                        fauna.getDescripcion(),
                                        fauna.getAprobada(),
                                        new UsuarioSalidaDto(
                                                fauna.getUsuario().getId(),
                                                fauna.getUsuario().getNombre(),
                                                fauna.getUsuario().getApellidos(),
                                                fauna.getUsuario().getCorreo(),
                                                fauna.getUsuario().getPassword(),
                                                fauna.getUsuario().getVerificado(),
                                                fauna.getUsuario().getRol()
                                        )
                                )
                        )
                .collect(Collectors.toList())
        );
    }

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
                                new UsuarioSalidaDto(
                                        fauna.getUsuario().getId(),
                                        fauna.getUsuario().getNombre(),
                                        fauna.getUsuario().getApellidos(),
                                        fauna.getUsuario().getCorreo(),
                                        fauna.getUsuario().getPassword(),
                                        fauna.getUsuario().getVerificado(),
                                        fauna.getUsuario().getRol()
                                )

        );

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createFauna(@RequestBody FaunaEntradaDto dto){
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
                new UsuarioSalidaDto(
                        faunaGuardada.getUsuario().getId(),
                        faunaGuardada.getUsuario().getNombre(),
                        faunaGuardada.getUsuario().getApellidos(),
                        faunaGuardada.getUsuario().getCorreo(),
                        faunaGuardada.getUsuario().getPassword(),
                        faunaGuardada.getUsuario().getVerificado(),
                        faunaGuardada.getUsuario().getRol()
                )

        );

        return ResponseEntity.ok(dtoSalida);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateAlumno(@RequestBody FaunaEntradaDto dto){
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
     */
}
