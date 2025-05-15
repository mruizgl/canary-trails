package es.iespuertodelacruz.mp.canarytrails.controller.admin;

import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.auth.UsuarioRegisterDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        //TODO: pasar por el dto, comentar como en los otros controller
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findUsuarioById(@PathVariable Integer id){

        Usuario usuario = usuarioService.findById(id);

        if(usuario == null){
            return ResponseEntity.notFound().build();
        }

        //TODO: devolver como dto
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/add")
    public ResponseEntity<?> createUsuario(@RequestBody UsuarioEntradaCreateDto dto){


        //TODO: mapear ode dto create a entidad, hacer el save

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.nombre());
        usuario.setApellidos(dto.apellidos());
        usuario.setCorreo(dto.correo());
        usuario.setPassword(dto.password());

        //TODO: mapeado a dto salida
        return ResponseEntity.ok(usuarioService.save(usuario));
    }

    //TODO: UPDATE mapear de Entrada update a dto, hacer el update, devolver un response entity con el usuarioService.update(usuario)

    //TODO: DELETE tal cual los otros delete, pero haciendo un catch del runtime exception (ejemplo sencillo en controller de zona)

}
