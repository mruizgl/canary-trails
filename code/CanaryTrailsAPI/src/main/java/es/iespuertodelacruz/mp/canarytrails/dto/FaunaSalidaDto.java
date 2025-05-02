package es.iespuertodelacruz.mp.canarytrails.dto;

public record FaunaSalidaDto(
        int id,
        String nombre,
        String descripcion,
        boolean aprobada,
        UsuarioSalidaDto usuario
) {}
