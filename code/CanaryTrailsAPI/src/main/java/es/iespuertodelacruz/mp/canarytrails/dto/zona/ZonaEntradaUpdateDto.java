package es.iespuertodelacruz.mp.canarytrails.dto.zona;

import java.util.List;

public record ZonaEntradaUpdateDto(
        Integer id,
        String nombre,
        List<Integer> municipios
) {
}
