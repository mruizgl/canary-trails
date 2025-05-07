package es.iespuertodelacruz.mp.canarytrails.dto.municipio;

public record MunicipioEntradaDto(
        String nombre,
        int altitudMedia,
        double latitudGeografica,
        double longitudGeografica,
        int zonaId
) {}