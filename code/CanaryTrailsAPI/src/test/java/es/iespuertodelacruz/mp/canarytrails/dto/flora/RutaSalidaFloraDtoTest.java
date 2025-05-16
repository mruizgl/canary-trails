package es.iespuertodelacruz.mp.canarytrails.dto.flora;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RutaSalidaFloraDtoTest {

    private static String MESSAGE_ERROR = "No se ha obtenido el resultado esperado";

    @Test
    void testRutaSalidaFloraDto() {

        RutaSalidaFloraDto dto = new RutaSalidaFloraDto(1, "Ruta del Teide", "Media", 7200L,
                8500.5f, 1200.75f, true);

        assertEquals(1, dto.id(), MESSAGE_ERROR);
        assertEquals("Ruta del Teide", dto.nombre(), MESSAGE_ERROR);
        assertEquals("Media", dto.dificultad(), MESSAGE_ERROR);
        assertEquals(7200L, dto.tiempoDuracion(), MESSAGE_ERROR);
        assertEquals(8500.5f, dto.distanciaMetros(), MESSAGE_ERROR);
        assertEquals(1200.75f, dto.desnivel(), MESSAGE_ERROR);
        assertTrue(dto.aprobada(), MESSAGE_ERROR);
    }
}
