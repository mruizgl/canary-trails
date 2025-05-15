package es.iespuertodelacruz.mp.canarytrails.dto.usuario.auth;

public record UsuarioRegisterDto(
        String nombre,
        String apellidos,
        String correo,
        String password
)
{}
