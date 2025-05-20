package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

/**
 * Salida de Flora que se carga en la salida de Ruta con el contenido que se necesita
 * @param id
 * @param nombre
 * @param especie
 * @param tipoHoja
 * @param salidaFlor
 * @param caidaFlor
 * @param descripcion
 * @param aprobada
 */
public record FloraSalidaRutaDto(
         int id,
         String nombre,
         String especie,
         String tipoHoja,
         String salidaFlor,
         String caidaFlor,
         String descripcion,
         boolean aprobada,
         String foto
) {
}