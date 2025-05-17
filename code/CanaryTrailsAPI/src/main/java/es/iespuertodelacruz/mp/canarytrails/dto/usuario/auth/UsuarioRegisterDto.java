package es.iespuertodelacruz.mp.canarytrails.dto.usuario.auth;

public record UsuarioRegisterDto(
        String nombre,
        String correo,
        String password
)
{}
