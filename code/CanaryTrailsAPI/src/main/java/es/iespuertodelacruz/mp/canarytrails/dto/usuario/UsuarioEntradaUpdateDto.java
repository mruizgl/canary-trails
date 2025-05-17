package es.iespuertodelacruz.mp.canarytrails.dto.usuario;


public record UsuarioEntradaUpdateDto (
        int id,
        String nombre,
        String correo,
        String password,
        boolean verificado,
        String rol
) {
}
