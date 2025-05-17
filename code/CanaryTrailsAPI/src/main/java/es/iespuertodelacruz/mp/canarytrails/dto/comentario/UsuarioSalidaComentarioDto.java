package es.iespuertodelacruz.mp.canarytrails.dto.comentario;

public record UsuarioSalidaComentarioDto(
        Integer id,
        String nombre,
        String correo,
        String password,
        boolean verificado,
        String rol
) {
}
