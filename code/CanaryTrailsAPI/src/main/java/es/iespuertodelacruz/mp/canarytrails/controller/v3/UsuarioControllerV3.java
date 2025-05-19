package es.iespuertodelacruz.mp.canarytrails.controller.v3;

import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.UsuarioMapper;
import es.iespuertodelacruz.mp.canarytrails.service.FotoManagementService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller para los endpoints de Usuario desde el administrador.
 */
@RestController
@RequestMapping("/api/v3/usuarios")
@CrossOrigin
public class UsuarioControllerV3 {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioMapper usuarioMapper;

    @Autowired
    FotoManagementService fotoManagementService;

    /**
     * Endpoint para obtener todos los usuarios
     */
    @GetMapping
    public ResponseEntity<?> findAllUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        List<UsuarioSalidaDto> usuariosDto = usuarios.stream()
                .map(usuarioMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuariosDto);
    }

    /**
     * Endpoint para obtener un usuario a partir de su id
     * @param id del usuario que se quiere obtener
     * @return el usuario cuyo id coincide con el introducido
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findUsuarioById(@PathVariable Integer id) {

        Usuario usuario = usuarioService.findById(id);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        UsuarioSalidaDto dto = usuarioMapper.toDto(usuario);

        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint para añadir un usuario a la bbdd
     * @param dto con los datos que se deben introducir para crear el usuario
     * @return un JSON del usuario guardado
     */
    @PostMapping("/add")
    public ResponseEntity<?> createUsuario(@RequestBody UsuarioEntradaCreateDto dto) {

        Usuario usuario = usuarioMapper.toEntityCreate(dto);

        try {
            usuario = usuarioService.save(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(usuarioMapper.toDto(usuario));
    }

    /**
     * Endpoint que actualiza un usuario en la bbdd
     * @param dto con el objeto a actualizar, tiene que contener la id del objeto
     * @return true si se ha actualizado y false si no
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateUsuario(@RequestBody UsuarioEntradaUpdateDto dto) {

        Usuario usuario = usuarioMapper.toEntityUpdate(dto);

        boolean actualizado;
        try {
            actualizado = usuarioService.update(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(actualizado);
    }


    /**
     * Borra un usuario a partir de la id
     * @param id del usuario a borrar
     * @return true si se ha borrado, false si no
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable("id") int id) {
       return ResponseEntity.ok(usuarioService.deleteById(id));
    }

    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("id") Integer id, @RequestParam("file") MultipartFile file) {

        String mensaje = "";
        String categoria = "usuario";

        try {
            String namefile = fotoManagementService.save(file, categoria);
            mensaje = "" + namefile;

            Usuario usuario = usuarioService.findById(id);

            if(usuario == null){
                return ResponseEntity.notFound().build();
            }

            usuario.setFoto(namefile);
            usuarioService.uploadFoto(usuario);

            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            mensaje = "No se pudo subir el archivo: " + file.getOriginalFilename()
                    + ". Error: " + e.getMessage();
            return ResponseEntity.badRequest().body(mensaje);
        }
    }

}
