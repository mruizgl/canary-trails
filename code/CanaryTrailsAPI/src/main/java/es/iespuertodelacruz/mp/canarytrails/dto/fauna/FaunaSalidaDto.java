package es.iespuertodelacruz.mp.canarytrails.dto.fauna;

import es.iespuertodelacruz.mp.canarytrails.dto.usuario.UsuarioSalidaDtoV2;

public record FaunaSalidaDto(
        int id,
        String nombre,
        String descripcion,
        boolean aprobada,
        UsuarioSalidaDtoV2 usuario
) {}
