package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService implements IServiceGeneric<Usuario, Integer> {

    @Autowired
    UsuarioRepository usuarioRepository;


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

        if(object.getApellidos() == null){
            throw new RuntimeException("El usuario ha de tener apellidos");
        }

        // Minimos y máximos recomendados por OWASP (Open Web Application Security Project)
        int min = 8;
        int max = 255;

        if(object.getPassword() == null || object.getPassword().length() < min || object.getPassword().length() > max){
            throw new RuntimeException("El la contraseña no es valida");
        }

        //Si no tiene ningun rol se le asigna el de usuario por defecto
        if(object.getRol() == null){
            object.setRol("USER");
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

            if(object.getApellidos() != null){
                usuario.setApellidos(object.getApellidos());
            }

            if(object.getCorreo() != null){
                usuario.setCorreo(object.getCorreo());
            }

            if(object.getPassword() != null){
                usuario.setPassword(object.getPassword());
            }

            if(object.getRol() != null){
                usuario.setRol(object.getRol());
            }

            if(object.getVerificado() != null){
                usuario.setVerificado(object.getVerificado());
            }

            usuarioRepository.save(usuario);
            return true;
        } else{
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Integer id) {

        //obtener usuario a partir de la id

        //compruebas las relaciones del objeto

        //Si tiene alguna relación con rutas, faunas, floras, devolver un runtime exception

        //Si no, sigues con lo que está abajo

        int cantidad = usuarioRepository.deleteUsuarioBydId(id);
        //Si se ha borrado, sedevuelve true. Si no, false. No se da info por seguridad
        return cantidad > 0;
    }

}
