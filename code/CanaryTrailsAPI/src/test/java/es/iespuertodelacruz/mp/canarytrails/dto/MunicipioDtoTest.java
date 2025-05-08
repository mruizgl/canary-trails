package es.iespuertodelacruz.mp.canarytrails.dto;

import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MunicipioDtoTest {

    @Test
    void testMunicipioEntradaDto() {
        MunicipioEntradaDto dto = new MunicipioEntradaDto(
                "Tacoronte", 300, 28.4, -16.3, 1
        );

        assertEquals("Tacoronte", dto.nombre());
        assertEquals(300, dto.altitudMedia());
        assertEquals(28.4, dto.latitudGeografica());
        assertEquals(-16.3, dto.longitudGeografica());
        assertEquals(1, dto.zonaId());
    }

    @Test
    void testMunicipioSalidaDto() {
        ZonaSalidaDto zonaDto = new ZonaSalidaDto(2, "Norte");

        MunicipioSalidaDto dto = new MunicipioSalidaDto(
                1, "La Orotava", 390,
                28.3891, -16.5237, zonaDto
        );

        assertEquals(1, dto.id());
        assertEquals("La Orotava", dto.nombre());
        assertEquals(390, dto.altitudMedia());
        assertEquals(28.3891, dto.latitudGeografica());
        assertEquals(-16.5237, dto.longitudGeografica());
        assertEquals(zonaDto, dto.zona());
    }
}