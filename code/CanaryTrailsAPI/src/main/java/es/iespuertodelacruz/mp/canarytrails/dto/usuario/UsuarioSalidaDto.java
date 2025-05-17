package es.iespuertodelacruz.mp.canarytrails.dto.usuario;

import java.util.Date;
import java.util.List;

/**
 * Salida de Usuario con todas las relaciones cargadas
 * @param id
 * @param nombre
 * @param apellidos
 * @param correo
 * @param password
 * @param verificado
 * @param rol
 * @param faunas que ha creado
 * @param floras que ha creado
 * @param rutas que ha creado
 * @param comentarios que ha escrito
 * @param rutasFavoritas que ha aniadido
 */
public record UsuarioSalidaDto (
        int id,
        String nombre,
        String correo,
        String password,
        String tokenVerificacion,
        Date fechaCreacion,
        boolean verificado,
        String rol,
        List<FaunaSalidaUsuarioDto> faunas,
        List<FloraSalidaUsuarioDto> floras,
        List<RutaSalidaUsuarioDto> rutas,
        List<ComentarioSalidaUsuarioDto> comentarios,
        List<RutaFavoritaSalidaUsuarioDto> rutasFavoritas,
        String foto
) {
}
