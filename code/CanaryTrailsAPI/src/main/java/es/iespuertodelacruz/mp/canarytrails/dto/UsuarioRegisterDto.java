package es.iespuertodelacruz.mp.canarytrails.dto;

public record UsuarioRegisterDto(
        String nombre,
        String apellidos,
        String correo,
        String password
)
{}
