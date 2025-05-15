package es.iespuertodelacruz.mp.canarytrails.dto.zona;

import java.math.BigDecimal;

public record MunicipioSalidaZonaDto(
        Integer id,
        String nombre,
        Integer altitudMedia,
        BigDecimal latitudGeografica,
        BigDecimal longitudGeografica
) {
}
