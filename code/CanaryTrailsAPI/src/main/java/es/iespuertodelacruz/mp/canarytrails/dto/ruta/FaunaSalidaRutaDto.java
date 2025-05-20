package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

/**
 * Salida de Fauna que se carga en la salida de Ruta con el contenido necesario
 * @param id
 * @param nombre
 * @param descripcion
 * @param aprobada
 */
public record FaunaSalidaRutaDto(
        int id,
        String nombre,
        String descripcion,
        boolean aprobada,
        String foto
) {
}