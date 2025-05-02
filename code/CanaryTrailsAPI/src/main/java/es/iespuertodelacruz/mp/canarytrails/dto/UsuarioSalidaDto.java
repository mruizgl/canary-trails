package es.iespuertodelacruz.mp.canarytrails.dto;

import java.util.List;

public record UsuarioSalidaDto (
        int id,
        String nombre,
        String apellidos,
        String correo,
        String password,
        boolean verificado,
        String rol
) {}
