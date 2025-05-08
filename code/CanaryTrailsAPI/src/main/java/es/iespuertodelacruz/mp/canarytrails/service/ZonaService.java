package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.ICrudService;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.repository.MunicipioRepository;
import es.iespuertodelacruz.mp.canarytrails.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ZonaService implements ICrudService<Zona, Integer> {

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private ZonaRepository zonaRepository;


    @Override
    public List<Zona> findAll() {
        return zonaRepository.findAll();
    }

    @Override
    public Optional<Zona> findById(Integer id) {
        return zonaRepository.findById(id);
    }

    @Override
    public Zona save(Zona zona) {
        return zonaRepository.save(zona);
    }

    @Override
    public void deleteById(Integer id) {
        zonaRepository.deleteById(id);
    }

    public void eliminarZonaPorId(int zonaId) {
        Optional<Zona> zonaOptional = zonaRepository.findById(zonaId);

        if (zonaOptional.isEmpty()) {
            throw new NoSuchElementException("La zona con ID " + zonaId + " no existe.");
        }

        List<Municipio> asociados = municipioRepository.findByZonaId(zonaId);
        if (!asociados.isEmpty()) {
            String nombres = asociados.stream()
                    .map(Municipio::getNombre)
                    .collect(Collectors.joining(", "));
            throw new IllegalStateException(
                    "No se puede eliminar la zona porque está asociada a los siguientes municipios: " + nombres +
                            ". Desasócialos primero para poder eliminarla.");
        }

        zonaRepository.deleteById(zonaId);
    }

}
