package es.iespuertodelacruz.mp.canarytrails.dto.usuario;

public record UsuarioEntradaDto(
        String nombre,
        String apellidos,
        String correo,
        String password,
        boolean verificado,
        String rol
) {
}
