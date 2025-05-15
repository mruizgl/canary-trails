package es.iespuertodelacruz.mp.canarytrails.dto.fauna;

import java.util.List;

/**
 * Salida de Fauna con las relaciones cargadas
 * @param id
 * @param nombre
 * @param descripcion
 * @param aprobada
 * @param usuario que la creo
 * @param rutas en las que aparece
 */
public record FaunaSalidaDto(
        int id,
        String nombre,
        String descripcion,
        boolean aprobada,
        UsuarioSalidaFaunaDto usuario,
        List<RutaSalidaFaunaDto> rutas,
        String foto
) {}
