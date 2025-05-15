package es.iespuertodelacruz.mp.canarytrails.dto.flora;

import java.util.List;

public record FloraEntradaCreateDto(
        String nombre,
        String especie,
        String tipoHoja,
        String salidaFlor,
        String caidaFlor,
        String descripcion,
        boolean aprobada,
        Integer usuario,
        List<Integer> rutas
) {
}
