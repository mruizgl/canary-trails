package es.iespuertodelacruz.mp.canarytrails.dto.usuario;

import java.util.List;

public record UsuarioSalidaDtoV2(
        int id,
        String nombre,
        String apellidos,
        boolean verificado,
        String rol,
        List<FaunaSalidaUsuarioDto> faunas,
        List<FloraSalidaUsuarioDto> floras,
        List<RutaSalidaUsuarioDto> rutas,
        String foto
) {}
