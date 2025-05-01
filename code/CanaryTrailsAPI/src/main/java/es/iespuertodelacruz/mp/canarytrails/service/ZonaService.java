package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.ICrudService;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ZonaService implements ICrudService<Zona, Integer> {

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
}
