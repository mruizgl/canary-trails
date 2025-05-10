package es.iespuertodelacruz.mp.canarytrails.dto.usuario;

/**
 * Salida de Flora cargada en Usuario con el contenido necesario
 * @param id
 * @param nombre
 * @param especie
 * @param tipoHoja
 * @param salidaFlor
 * @param caidaFlor
 * @param descripcion
 * @param aprobada
 */
public record FloraSalidaUsuarioDto(
        int id,
        String nombre,
        String especie,
        String tipoHoja,
        String salidaFlor,
        String caidaFlor,
        String descripcion,
        boolean aprobada
) {
}
