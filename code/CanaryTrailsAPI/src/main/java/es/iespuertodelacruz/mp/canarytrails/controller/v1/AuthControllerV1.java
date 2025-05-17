package es.iespuertodelacruz.mp.canarytrails.controller.v1;


import es.iespuertodelacruz.mp.canarytrails.dto.usuario.auth.UsuarioLoginDto;
import es.iespuertodelacruz.mp.canarytrails.dto.usuario.auth.UsuarioRegisterDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.repository.UsuarioRepository;
import es.iespuertodelacruz.mp.canarytrails.security.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthControllerV1 {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UsuarioRegisterDto userDto ) {
        Usuario usuario = new Usuario();
        usuario.setNombre(userDto.nombre());
        usuario.setCorreo(userDto.correo());
        usuario.setPassword(userDto.password());
        boolean registrado = authService.register(usuario);

        if(registrado){
            return ResponseEntity.ok("Usuario registrado. Compruebe su correo para verificar que es usted");
        } else {
            return ResponseEntity.ok("Ha habido un error al registrar el usuario");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginDto userLogin ) {

        String token = authService.authenticate(userLogin.nombre(), userLogin.password());

        if ( token == null ) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(token);

    }

    @GetMapping("/confirmacion/")
    public ResponseEntity<?> confirmation (@RequestParam String correo, @RequestParam String token){

        Usuario authUsuario = usuarioRepository.findByCorreo(correo).orElse(null);

       // System.out.println("Estoy con el usuario "+authUsuario);
        if(authUsuario != null) {
            String tokenDB = authUsuario.getTokenVerificacion();
            //System.out.println("Estoy con el token de la bbdd "+tokenDB);
            if(tokenDB != null && tokenDB.equals(token)) {
                //System.out.println("El token es el mismo que la bbdd ");
                authUsuario.setVerificado(true);
                usuarioRepository.save(authUsuario);
                //logger.info("Cuenta verificada");
                return ResponseEntity.ok("Cuenta verificada.");

            } else {
                //logger.info("Token de verificacion invalido.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token de verificacion invalido.");
            }
        } else {
            //logger.info("Usuario no encontrado.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }
}
