package es.iespuertodelacruz.mp.canarytrails.dto.municipio;

public record MunicipioEntradaDto(
        int id,
        String nombre,
        int altitudMedia,
        double latitudGeografica,
        double longitudGeografica,
        int zonaId
) {}