package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.repository.MunicipioRepository;
import es.iespuertodelacruz.mp.canarytrails.repository.ZonaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MunicipioServiceTest {

    @InjectMocks
    private MunicipioService municipioService;

    @Mock
    private MunicipioRepository municipioRepository;

    @Mock
    private ZonaRepository zonaRepository;

    private Municipio municipioValido;
    private Zona zona;

    @BeforeEach
    public void setUp() {
        zona = new Zona();
        zona.setId(1);
        zona.setNombre("Norte");

        municipioValido = new Municipio();
        municipioValido.setId(1);
        municipioValido.setNombre("La Laguna");
        municipioValido.setAltitudMedia(600);
        municipioValido.setLatitudGeografica(BigDecimal.valueOf(28.4850));
        municipioValido.setLongitudGeografica(BigDecimal.valueOf(-16.3167));
        municipioValido.setZona(zona);
    }

    @Test
    public void saveMunicipioValidoTest() {
        when(municipioRepository.save(municipioValido)).thenReturn(municipioValido);

        Municipio result = municipioService.save(municipioValido);

        assertNotNull(result);
        assertEquals("La Laguna", result.getNombre());
    }

    @Test
    public void saveMunicipioSinNombre_LanzaExcepcion() {
        municipioValido.setNombre(null);
        assertThrows(RuntimeException.class, () -> municipioService.save(municipioValido));
    }

    @Test
    public void updateMunicipioExistenteTest() {
        when(municipioRepository.findById(1)).thenReturn(Optional.of(municipioValido));
        when(municipioRepository.save(any())).thenReturn(municipioValido);

        boolean actualizado = municipioService.update(municipioValido);

        assertTrue(actualizado);
        verify(municipioRepository).deleteRutaMunicipioRelation(1);
    }

    @Test
    public void updateMunicipioInexistente_LanzaExcepcion() {
        Municipio inexistente = new Municipio();
        inexistente.setId(999);
        when(municipioRepository.findById(999)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> municipioService.update(inexistente));
        assertTrue(ex.getMessage().contains("No existe"));
    }

    @Test
    public void deleteMunicipioSinRutasTest() {
        when(municipioRepository.findById(1)).thenReturn(Optional.of(municipioValido));
        when(municipioRepository.deleteMunicipioById(1)).thenReturn(1);
        municipioValido.setRutas(List.of()); // No tiene rutas

        boolean eliminado = municipioService.deleteById(1);

        assertTrue(eliminado);
    }

    @Test
    public void deleteMunicipioConRutas_LanzaExcepcion() {
        Ruta ruta = new Ruta();
        ruta.setId(1);
        municipioValido.setRutas(List.of(ruta));
        when(municipioRepository.findById(1)).thenReturn(Optional.of(municipioValido));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> municipioService.deleteById(1));
        assertTrue(ex.getMessage().contains("no puede contener rutas"));
    }

    @Test
    public void findByIdTest() {
        when(municipioRepository.findById(1)).thenReturn(Optional.of(municipioValido));

        Municipio result = municipioService.findById(1);

        assertNotNull(result);
        assertEquals("La Laguna", result.getNombre());
    }

    @Test
    public void findAllTest() {
        when(municipioRepository.findAll()).thenReturn(List.of(municipioValido));

        List<Municipio> result = municipioService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void saveMunicipioNombreVacio_LanzaExcepcion() {
        municipioValido.setNombre("   ");
        RuntimeException ex = assertThrows(RuntimeException.class, () -> municipioService.save(municipioValido));
        assertTrue(ex.getMessage().contains("nombre"));
    }

    @Test
    public void saveMunicipioSinAltitudMedia_LanzaExcepcion() {
        municipioValido.setAltitudMedia(null);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> municipioService.save(municipioValido));
        assertTrue(ex.getMessage().contains("altitud"));
    }

    @Test
    public void saveMunicipioSinLatitudGeografica_LanzaExcepcion() {
        municipioValido.setLatitudGeografica(null);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> municipioService.save(municipioValido));
        assertTrue(ex.getMessage().contains("latitud"));
    }

    @Test
    public void saveMunicipioSinLongitudGeografica_LanzaExcepcion() {
        municipioValido.setLongitudGeografica(null);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> municipioService.save(municipioValido));
        assertTrue(ex.getMessage().contains("longitud"));
    }

    @Test
    public void saveMunicipioSinZona_LanzaExcepcion() {
        municipioValido.setZona(null);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> municipioService.save(municipioValido));
        assertTrue(ex.getMessage().contains("zona"));
    }

    @Test
    public void saveMunicipioConZonaSinId_LanzaExcepcion() {
        municipioValido.setZona(new Zona()); // id null
        RuntimeException ex = assertThrows(RuntimeException.class, () -> municipioService.save(municipioValido));
        assertTrue(ex.getMessage().contains("zona"));
    }

    @Test
    public void saveMunicipioConRutas_AgregaRelaciones() {
        Ruta ruta = new Ruta();
        ruta.setId(42);
        municipioValido.setRutas(List.of(ruta));
        when(municipioRepository.save(municipioValido)).thenReturn(municipioValido);

        Municipio result = municipioService.save(municipioValido);

        verify(municipioRepository).addRutaMunicipioRelation(municipioValido.getId(), ruta.getId());
        assertEquals("La Laguna", result.getNombre());
    }


}
