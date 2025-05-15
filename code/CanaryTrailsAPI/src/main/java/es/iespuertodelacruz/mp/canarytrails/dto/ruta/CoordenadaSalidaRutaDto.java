package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

import java.math.BigDecimal;

public record CoordenadaSalidaRutaDto(
        int id,
        BigDecimal latitud,
        BigDecimal longitud
) {
}
