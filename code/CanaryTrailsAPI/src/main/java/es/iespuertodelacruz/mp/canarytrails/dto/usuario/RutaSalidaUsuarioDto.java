package es.iespuertodelacruz.mp.canarytrails.dto.usuario;

/**
 * Salida de Ruta cargada en Usuario con el contenido necesario
 * @param id
 * @param nombre
 * @param dificultad
 * @param tiempoDuracion
 * @param distanciaMetros
 * @param desnivel
 * @param aprobada
 */
public record RutaSalidaUsuarioDto(
        int id,
        String nombre,
        String dificultad,
        long tiempoDuracion,
        float distanciaMetros,
        float desnivel,
        boolean aprobada
) {
}
