package es.iespuertodelacruz.mp.canarytrails.dto.flora;

public record UsuarioSalidaFloraDto(
        int id,
        String nombre,
        String apellidos,
        String correo,
        String password,
        boolean verificado,
        String rol
) {
}
