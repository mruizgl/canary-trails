package es.iespuertodelacruz.mp.canarytrails.dto.municipio;


import java.math.BigDecimal;
import java.util.List;

public record MunicipioEntradaUpdateDto(
        Integer id,
        String nombre,
        Integer altitudMedia,
        BigDecimal latitudGeografica,
        BigDecimal longitudGeografica,
        Integer zona,
        List<Integer> rutas
) {}

