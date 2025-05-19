package es.iespuertodelacruz.mp.canarytrails.security;

import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.repository.UsuarioRepository;
import es.iespuertodelacruz.mp.canarytrails.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Transactional
    public boolean register(Usuario usuario) {

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setVerificado(false);
        usuario.setFoto("src/main/resources/uploads/usuario/default.png");
        usuario.setRol("ROLE_USER");

        String tokenVerifCorreo = UUID.randomUUID().toString();
        usuario.setTokenVerificacion(tokenVerifCorreo);

        Date fechaActual = new Date();
        usuario.setFechaCreacion(fechaActual);

        Usuario saved = usuarioRepository.save(usuario);

        if(saved != null) {
            String senders[] = {"apps.akameterindustries@gmail.com", usuario.getCorreo()};
            mailService.send(senders, "Usuario creado: "+usuario.getNombre(),
                    "http://localhost:8080/api/v1/auth/confirmacion/?correo="+usuario.getCorreo()+"&token="+tokenVerifCorreo);
            //return jwtService.generateToken(usuario.getNombre(), usuario.getRol());
            return true;
        }else {
            return false;
        }
    }


    public String authenticate(String username, String password)  {
        String generatedToken = null;
        Usuario usuario = usuarioRepository.findByNombre(username).orElse(null);

        if (usuario != null) {

            if(!usuario.getVerificado()){
                return generatedToken;
            }

            if (passwordEncoder.matches(password, usuario.getPassword())) {
                generatedToken = jwtService.generateToken(usuario.getNombre(), usuario.getRol());
            }
        }

        return generatedToken;
    }
}
