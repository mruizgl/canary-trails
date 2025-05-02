package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.ICrudService;
import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import es.iespuertodelacruz.mp.canarytrails.repository.CoordenadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoordenadaService implements ICrudService<Coordenada, Integer> {

    @Autowired
    private CoordenadaRepository coordenadaRepository;

    @Override
    public List<Coordenada> findAll() {
        return coordenadaRepository.findAll();
    }

    @Override
    public Optional<Coordenada> findById(Integer id) {
        return coordenadaRepository.findById(id);
    }

    @Override
    public Coordenada save(Coordenada coordenada) {
        return coordenadaRepository.save(coordenada);
    }

    @Override
    public void deleteById(Integer id) {
        coordenadaRepository.deleteById(id);
    }
}
