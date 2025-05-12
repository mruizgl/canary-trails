package es.iespuertodelacruz.mp.canarytrails.dto.municipio;


import java.util.List;

public record MunicipioSalidaDto(
        int id,
        String nombre,
        int altitudMedia,
        double latitudGeografica,
        double longitudGeografica,
        ZonaSalidaMunicipioDto zona,
        List<RutaSalidaMunicipioDto> rutas
) {}