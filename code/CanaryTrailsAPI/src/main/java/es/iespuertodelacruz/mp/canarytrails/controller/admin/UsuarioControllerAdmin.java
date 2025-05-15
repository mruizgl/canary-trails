package es.iespuertodelacruz.mp.canarytrails.controller.admin;

import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.auth.UsuarioRegisterDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.service.FotoManagementService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    FotoManagementService fotoManagementService;

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

    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("id") Integer id, @RequestParam("file") MultipartFile file) {

        String mensaje = "";
        String categoria = "usuario";

        try {
            String namefile = fotoManagementService.save(file, categoria);
            mensaje = "" + namefile;

            Usuario usuario = usuarioService.findById(id);

            usuario.setFoto(namefile);
            usuarioService.update(usuario);

            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            mensaje = "No se pudo subir el archivo: " + file.getOriginalFilename()
                    + ". Error: " + e.getMessage();
            return ResponseEntity.badRequest().body(mensaje);
        }
    }

}
