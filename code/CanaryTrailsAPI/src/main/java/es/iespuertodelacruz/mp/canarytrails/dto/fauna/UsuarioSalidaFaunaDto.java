package es.iespuertodelacruz.mp.canarytrails.dto.fauna;

/**
 * Salida de Usuario que se carga en la salida de Fauna con el contenido que se necesita
 * @param id
 * @param nombre
 * @param correo
 * @param password
 * @param verificado
 * @param rol
 */
public record UsuarioSalidaFaunaDto(
        int id,
        String nombre,
        String correo,
        String password,
        boolean verificado,
        String rol
) {
}
