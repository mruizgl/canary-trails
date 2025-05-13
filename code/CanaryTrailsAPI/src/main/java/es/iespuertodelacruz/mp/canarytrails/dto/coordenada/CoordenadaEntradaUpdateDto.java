package es.iespuertodelacruz.mp.canarytrails.dto.coordenada;

import java.math.BigDecimal;
import java.util.List;

public record CoordenadaEntradaUpdateDto (
        int id,
        BigDecimal latitud,
        BigDecimal longitud
        //List<Integer> rutas
) {
}
