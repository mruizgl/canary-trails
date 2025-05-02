package es.iespuertodelacruz.mp.canarytrails.dto.usuario;

/**
 * Dto con la información de salida de usuario
 * V2: Ignora las relaciones, salida simple
 * @param id
 * @param nombre
 * @param apellidos
 * @param correo
 * @param password
 * @param verificado
 * @param rol
 */
public record UsuarioSalidaDtoV2(
        int id,
        String nombre,
        String apellidos,
        String correo,
        String password,
        boolean verificado,
        String rol
) {}
