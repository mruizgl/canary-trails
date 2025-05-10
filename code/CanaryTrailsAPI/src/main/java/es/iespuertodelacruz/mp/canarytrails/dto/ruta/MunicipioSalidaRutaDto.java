package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

/**
 * Salida de Municipio que se carga en la salida de Ruta con el contenido necesario
 * @param id
 * @param nombre
 * @param altitudMedia
 * @param latitud
 * @param longitud
 */
public record MunicipioSalidaRutaDto(
        int id,
        String nombre,
        int altitudMedia,
        float latitud,
        float longitud
) {
}
