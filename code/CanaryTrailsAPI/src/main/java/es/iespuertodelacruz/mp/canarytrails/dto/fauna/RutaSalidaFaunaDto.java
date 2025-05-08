package es.iespuertodelacruz.mp.canarytrails.dto.fauna;

/**
 * Salida de Ruta que se carga en la salida de Fauna con el contenido que se necesita
 * @param id
 * @param nombre
 * @param dificultad
 * @param tiempoDuracion
 * @param distanciaMetros
 * @param desnivel
 * @param aprobada
 */
public record RutaSalidaFaunaDto (
        int id,
        String nombre,
        String dificultad,
        long tiempoDuracion,
        float distanciaMetros,
        float desnivel,
        boolean aprobada
) {
}
