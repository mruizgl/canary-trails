package es.iespuertodelacruz.mp.canarytrails.dto.municipio;

public record MunicipiosSalidaDtoV2(
       int id,
       String nombre,
       int altitudMedia,
       double latitudGeografica,
       double longitudGeografica
) {}
