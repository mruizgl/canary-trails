package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.repository.CoordenadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CoordenadaService implements IServiceGeneric<Coordenada, Integer> {

    @Autowired
    private CoordenadaRepository coordenadaRepository;

    @Override
    public List<Coordenada> findAll() {
        return coordenadaRepository.findAll();
    }

    @Override
    public Coordenada findById(Integer id) {
        return coordenadaRepository.findById(id).orElse(null);
    }

    public Coordenada findByData(BigDecimal latitud, BigDecimal longitud){
        return coordenadaRepository.findByLatitudAndLongitud(latitud, longitud).orElse(null);
    }

    @Override
    @Transactional
    public Coordenada save(Coordenada coordenada) {

        if(coordenada.getLatitud() == null){
            throw new RuntimeException("La latitud no puede estar vacia");
        }

        if(coordenada.getLongitud() == null){
            throw new RuntimeException("La altitud no puede estar vacía");
        }

        /*if (savedCoordenada.getRutas() != null && !savedCoordenada.getRutas().isEmpty()) {
            for (Ruta ruta : savedCoordenada.getRutas()) {
                coordenadaRepository.addRutaCoordenadaRelation(savedCoordenada.getId(), ruta.getId());
            }
        }*/

        return coordenadaRepository.save(coordenada);
    }

    @Override
    @Transactional
    public boolean update(Coordenada object) {
        if(object != null && object.getId() != null) {

            Coordenada coordenada = coordenadaRepository.findById(object.getId()).orElse(null);

            if(coordenada == null){
                throw new RuntimeException("No existe la coordenada " +object.getId());
                //TODO: cambiarlo por un false ?
            }

            if(object.getLongitud() != null){
                coordenada.setLongitud(object.getLongitud());
            }

            if(object.getLatitud() != null){
                coordenada.setLatitud(object.getLatitud());
            }

            /*if(object.getRutas() != null && !object.getRutas().isEmpty()){
                coordenada.setRutas(object.getRutas());
            }*/

            Coordenada savedCoordenada = coordenadaRepository.save(coordenada);

            // Se borran las relaciones siempre. Si hay nuevas se actualizan, si no se quiere actualizar se tienen q
            // poner las id de las que ya estaban, y si no se pone ninguna o un 0, se borran todas las relaciones
           /* int cantidad = coordenadaRepository.deleteRutaCoordenadaRelation(savedCoordenada.getId());

            if (savedCoordenada.getRutas() != null && !savedCoordenada.getRutas().isEmpty()) {
                for (Ruta ruta : object.getRutas()) {
                    coordenadaRepository.addRutaCoordenadaRelation(savedCoordenada.getId(), ruta.getId());
                }
            }*/

            return true;
        } else{
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Integer id) {
        int cantidad = coordenadaRepository.deleteCoordenadaById(id);
        return cantidad > 0;
    }

    /*public List<Coordenada> saveAll(List<Coordenada> coordenadas) {
        return coordenadaRepository.saveAll(coordenadas);
    }*/
}
