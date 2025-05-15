package es.iespuertodelacruz.mp.canarytrails.dto.zona;

import java.util.List;

public record ZonaSalidaDto(
        Integer id,
        String nombre,
        List<MunicipioSalidaZonaDto> municipios
) {
}
