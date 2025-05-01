package es.iespuertodelacruz.mp.canarytrails.controller;

import es.iespuertodelacruz.mp.canarytrails.dto.UsuarioRegisterDto;
import es.iespuertodelacruz.mp.canarytrails.dto.UsuarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * Controller para los endpoints de Usuario.
 * /api/v1/admin/usuarios indica que es la versión para administradores y el endpoint de usuarios
 * En caso de cambiar la versión de la api, cambiaria a v2
 */
@RestController
@RequestMapping("/api/v1/admin/usuarios")
@CrossOrigin
public class UsuarioControllerAdmin {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> findAllUsuarios(){
        System.out.println("holaaaaaa estoy aqui");
        return ResponseEntity.ok(usuarioService.findAll()
                        /*.stream()
                        .map(usuario -> new UsuarioSalidaDto(
                                        usuario.getId(),
                                        usuario.getNombre(),
                                        usuario.getApellidos(),
                                        usuario.getCorreo(),
                                        usuario.getPassword(),
                                        usuario.getVerificado(),
                                        usuario.getRol(),


                                )
                        )
                .collect(Collectors.toList())*/
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUsuarioById(@PathVariable Integer id){

        Usuario usuario = usuarioService.findById(id);
        /*UsuarioSalidaDto dto = new UsuarioSalidaDto(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getCorreo(),
                usuario.getPassword(),
                usuario.getVerificado(),
                usuario.getRol(),

        );
        return ResponseEntity.ok(dto);*/

        if(usuario == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createUsuario(@RequestBody UsuarioRegisterDto dto){
        //Logger logger = Logger.getLogger("logger");
        //Logger logger = Logger.getLogger(Globals.LOGGER);
        //logger.info("Llamada al find all get /api/usuarios");
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setApellidos(dto.apellidos());
        usuario.setCorreo(dto.correo());
        usuario.setPassword(dto.password());

        return ResponseEntity.ok(usuarioService.save(usuario));
    }
}
