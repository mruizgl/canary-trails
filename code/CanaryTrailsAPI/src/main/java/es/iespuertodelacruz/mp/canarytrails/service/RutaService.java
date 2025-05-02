package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutaService implements IServiceGeneric<Ruta, Integer> {

    @Autowired
    RutaRepository rutaRepository;

    @Override
    public List<Ruta> findAll() {
        return rutaRepository.findAll();
    }

    @Override
    public Ruta findById(Integer id) {
        return rutaRepository.findById(id).orElse(null);
    }

    @Override
    public Ruta save(Ruta object) {
        return null;
    }

    @Override
    public boolean update(Ruta object) {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {

        //Relacion N:M con Fauna
        rutaRepository.deleteRutaFaunaRelation(id);
        //Relacion N:M con Flora
        rutaRepository.deleteRutaFloraRelation(id);
        //Relacion N:M con Usuario en tabla de favoritas
        rutaRepository.deleteRutaFavoritaRelation(id);
        //Relacion N:M con Municipio
        rutaRepository.deleteRutaMunicipioRelation(id);
        //Relacion N:M con Coordenada
        rutaRepository.deleteRutaCoordenadaRelation(id);

        //La relación con Usuario es 1:N así q la hace JPA
        //La relación con comentarios es 1:N así que la hace JPA

        int cantidad = rutaRepository.deleteRutaById(id);
        return cantidad >0;
    }
}
