package es.iespuertodelacruz.mp.canarytrails.dto.municipio;


import java.math.BigDecimal;

public record MunicipioEdicionDto(
        Integer id,
        String nombre,
        Integer altitudMedia,
        BigDecimal latitudGeografica,
        BigDecimal longitudGeografica,
        Integer zonaId
) {}

