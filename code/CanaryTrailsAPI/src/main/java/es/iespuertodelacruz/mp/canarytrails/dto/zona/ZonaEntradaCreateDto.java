package es.iespuertodelacruz.mp.canarytrails.dto.zona;


import java.util.List;

public record ZonaEntradaCreateDto(
        String nombre,
        List<Integer> municipios
) {
}
