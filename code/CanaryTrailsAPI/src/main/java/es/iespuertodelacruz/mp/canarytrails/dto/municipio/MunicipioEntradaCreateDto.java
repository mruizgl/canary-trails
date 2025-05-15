package es.iespuertodelacruz.mp.canarytrails.dto.municipio;

import java.util.List;

public record MunicipioEntradaCreateDto(
        String nombre,
        int altitudMedia,
        double latitudGeografica,
        double longitudGeografica,
        Integer zona,
        List<Integer> rutas 
) {}