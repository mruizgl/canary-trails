package es.iespuertodelacruz.mp.canarytrails.controller.v2;

import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.UsuarioMapper;
import es.iespuertodelacruz.mp.canarytrails.service.FotoManagementService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller para los endpoints de Usuario desde el administrador.
 */
@RestController
@RequestMapping("/api/v2/usuarios")
@CrossOrigin
public class UsuarioControllerV2 {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioMapper usuarioMapper;

    @Autowired
    FotoManagementService fotoManagementService;

    /**
     * Endpoint para obtener un usuario a partir de su id
     * @param id del usuario que se quiere obtener
     * @return el usuario cuyo id coincide con el introducido
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<?> findUsuarioById(@PathVariable Integer id) {

        Usuario usuario = usuarioService.findById(id);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        if(!esSuPerfil(usuario)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se pueden ver el perfil de otro usuario");
        }

        UsuarioSalidaDto dto = usuarioMapper.toDto(usuario);

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> findUsuarioByName(@PathVariable String nombre) {

        Usuario usuario = usuarioService.findByUserName(nombre);

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }

        if(!esSuPerfil(usuario)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se pueden ver el perfil de otro usuario");
        }

        UsuarioSalidaDto dto = usuarioMapper.toDto(usuario);

        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint que actualiza un usuario en la bbdd
     * @param dto con el objeto a actualizar, tiene que contener la id del objeto
     * @return true si se ha actualizado y false si no
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateUsuario(@RequestBody UsuarioEntradaUpdateDto dto) {

        Usuario usuario = usuarioMapper.toEntityUpdate(dto);

        if(!esSuPerfil(usuario)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se pueden actualizar el perfil de otro usuario");
        }

        if (usuarioService.findById(dto.id()).getVerificado()) {
            usuario.setVerificado(true);
        } else {
            usuario.setVerificado(false);
        }

        usuario.setRol("ROLE_USER");

        boolean actualizado;
        try {
            actualizado = usuarioService.update(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(actualizado);
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

            if(!esSuPerfil(usuario)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se pueden actualizar la foto de perfil de otro usuario");
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

    public boolean esSuPerfil(Usuario usuario) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuario.getNombre().equals(username);
    }

}
