package es.iespuertodelacruz.mp.canarytrails.dto.usuario;

import es.iespuertodelacruz.mp.canarytrails.dto.ruta.UsuarioSalidaRutaDto;

/**
 * Salida de Comentario cargada en Usuario con el contenido necesario
 * @param id
 * @param titulo
 * @param descripcion
 */
public record ComentarioSalidaUsuarioDto(
        int id,
        String titulo,
        String descripcion
) {
}
