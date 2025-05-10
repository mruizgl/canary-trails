package es.iespuertodelacruz.mp.canarytrails.dto.usuario;

/**
 * Salida de Fauna cargada en Usuario con el contenido necesario
 * @param id
 * @param nombre
 * @param descripcion
 * @param aprobada
 */
public record FaunaSalidaUsuarioDto(
        int id,
        String nombre,
        String descripcion,
        boolean aprobada
) {
}
