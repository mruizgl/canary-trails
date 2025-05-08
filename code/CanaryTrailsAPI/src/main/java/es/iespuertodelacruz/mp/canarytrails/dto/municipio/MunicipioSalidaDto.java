package es.iespuertodelacruz.mp.canarytrails.dto.municipio;

import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;

public record MunicipioSalidaDto(
        int id,
        String nombre,
        int altitudMedia,
        double latitudGeografica,
        double longitudGeografica,
        ZonaSalidaDto zona
) {}