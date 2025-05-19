package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService implements IServiceGeneric<Usuario, Integer> {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    /**
     * Metodo que devuelve todos los usuarios
     * @return todos los usuarios
     */
    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    /**
     * Devuelve el usuario especifico que tenga la id proporcionada
     * @param id proporcionada para buscar el usuario
     * @return usuario que tenga la id indicada
     */
    @Override
    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario findByUserName(String name){
        return usuarioRepository.findByNombre(name).orElse(null);
    }


    @Override
    @Transactional
    public Usuario save(Usuario object) {

        // Lookahead que comprueba que la longitud total es de 6 a 320 caracteres que son el mínimo
        // y máximos para un correo segun el RFC 5321 del Simple Mail Transfer Protocol
        String regex = "^(?=.{6,320}$)([A-Za-z0-9._%+-]{1,64})@([A-Za-z0-9.-]{1,255})\\.[A-Za-z]{2,63}$";

        if(object.getCorreo() == null || !object.getCorreo().matches(regex)){
            throw new RuntimeException("El correo no cumple la validacion");

        }

        if(object.getNombre() == null){
            throw new RuntimeException("El usuario ha de tener nombre");
        }

        String tokenVerifCorreo = UUID.randomUUID().toString();
        object.setTokenVerificacion(tokenVerifCorreo);

        Date fechaActual = new Date();
        object.setFechaCreacion(fechaActual);

        // Minimos y máximos recomendados por OWASP (Open Web Application Security Project)
        int min = 8;
        int max = 255;

        if(object.getPassword() == null || object.getPassword().length() < min || object.getPassword().length() > max){
            throw new RuntimeException("El la contraseña no es valida");
        }

        String passwordEncoded = passwordEncoder.encode(object.getPassword());
        object.setPassword(passwordEncoded);

        if(object.getFoto() == null){
            String defaultFoto = "src/main/resources/uploads/usuario/default.png";
            object.setFoto(defaultFoto);
        }

        //Si no tiene ningun rol se le asigna el de usuario por defecto
        if(object.getRol() == null){
            object.setRol("ROLE_USER");
        }

        return usuarioRepository.save(object);
    }

    @Override
    @Transactional
    public boolean update(Usuario object) {

        if(object != null && object.getId() != null) {

            Usuario usuario = usuarioRepository.findById(object.getId()).orElse(null);

            if(usuario == null){
                throw new RuntimeException("No existe el usuario " +object);
            }

            if(object.getNombre() != null){
                usuario.setNombre(object.getNombre());
            }

            String regex = "^(?=.{6,320}$)([A-Za-z0-9._%+-]{1,64})@([A-Za-z0-9.-]{1,255})\\.[A-Za-z]{2,63}$";
            if(object.getCorreo() != null){

                if(!object.getCorreo().matches(regex)){
                    throw new RuntimeException("El correo no es valido");
                }

                usuario.setCorreo(object.getCorreo());
            }

            int min = 8;
            int max = 255;

            if(object.getPassword() != null){
                if(object.getPassword().length() < min || object.getPassword().length() > max){
                    throw new RuntimeException("La contraseña no es valida");
                }
                usuario.setPassword(object.getPassword());
            }

            if(object.getRol() != null){
                usuario.setRol(object.getRol());
            }

            if(object.getVerificado() != null){
                usuario.setVerificado(object.getVerificado());
            }

            if(object.getFoto() != null && !object.getFoto().isBlank()){
                usuario.setFoto(object.getFoto());
            } else {
                String defaultFoto = "src/main/resources/uploads/usuario/default.png";
                usuario.setFoto(defaultFoto);
            }

            usuarioRepository.save(usuario);
            return true;
        } else{
            return false;
        }
    }

    @Transactional
    public boolean uploadFoto(Usuario usuario){
        usuarioRepository.save(usuario);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteById(Integer id) {

        Usuario usuario = findById(id);

        if(usuario.getFaunas() != null || usuario.getRutas() != null || usuario.getFloras() != null || usuario.getComentarios() != null){
            usuario.setNombre("Usuario eliminado");
            usuarioRepository.save(usuario);
            return true;
        }

        int cantidad = usuarioRepository.deleteUsuarioBydId(id);
        return cantidad > 0;
    }

}
