package es.iespuertodelacruz.mp.canarytrails.dto.ruta;

import es.iespuertodelacruz.mp.canarytrails.dto.rutafavorita.ModificarRutaFavoritaDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ModificarRutaFavoritaDtoTest {

    private static String MESSAGE_ERROR = "No se ha obtenido el resultado esperado";

    @Test
    void modificarRutaFavoritaDtoTest() {
        int idUsuario = 10;
        int idRuta = 42;

        ModificarRutaFavoritaDto dto = new ModificarRutaFavoritaDto(idUsuario, idRuta);

        Assertions.assertEquals(idUsuario, dto.idUsuario(), MESSAGE_ERROR);
        Assertions.assertEquals(idRuta, dto.idRuta(), MESSAGE_ERROR);
    }
}
