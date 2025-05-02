package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.ICrudService;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.mapper.FloraMapper;
import es.iespuertodelacruz.mp.canarytrails.repository.FloraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FloraService implements ICrudService<Flora, Integer> {

    @Autowired
    private FloraRepository floraRepository;

    @Override
    public List<Flora> findAll() {
        return floraRepository.findAll();
    }

    @Override
    public Optional<Flora> findById(Integer id) {
        return floraRepository.findById(id);
    }

    @Override
    public Flora save(Flora flora) {
        return floraRepository.save(flora);
    }

    @Override
    public void deleteById(Integer id) {
        floraRepository.deleteById(id);
    }
}
