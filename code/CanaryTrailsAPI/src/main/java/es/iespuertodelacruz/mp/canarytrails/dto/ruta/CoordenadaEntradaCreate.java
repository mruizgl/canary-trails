package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

import java.math.BigDecimal;

public record CoordenadaEntradaCreate(
        BigDecimal latitud,
        BigDecimal longitud
) {
}
