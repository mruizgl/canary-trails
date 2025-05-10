package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

/**
 * Salida de Comentario que se carga en la salida de Ruta con el contenido necesario
 * @param id
 * @param titulo
 * @param descripcion
 * @param usuario que escribió el comentario
 */
public record ComentarioSalidaRutaDto(
        int id,
        String titulo,
        String descripcion,
        UsuarioSalidaRutaDto usuario
) {
}
