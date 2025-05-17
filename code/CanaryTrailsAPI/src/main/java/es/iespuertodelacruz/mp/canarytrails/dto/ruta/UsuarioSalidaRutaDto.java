package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

/**
 * Salida de Usuario que se carga en la salida de Ruta con el contenido que se necesita
 * @param id
 * @param nombre
 * @param correo
 * @param password
 * @param verificado
 * @param rol
 */
public record UsuarioSalidaRutaDto(
        int id,
        String nombre,
        String correo,
        String password,
        boolean verificado,
        String rol
) {
}
