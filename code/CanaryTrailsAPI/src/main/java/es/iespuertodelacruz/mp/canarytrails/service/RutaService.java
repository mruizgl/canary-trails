package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
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

        if(object.getNombre() == null){
            throw new RuntimeException("La ruta debe tener nombre");
        }

        //La dificultad se introduce o se hace un calculo según los datos?
        if(object.getDificultad() == null){
            throw new RuntimeException("La ruta debe tener dificultad");
        }

        if(object.getTiempoDuracion() == null){
            throw new RuntimeException("La ruta tiene que tener tiempo de duracion");
        }

        if(object.getDistanciaMetros() == null){
            throw new RuntimeException("La ruta ha de tener distancia");
        }

        if(object.getDesnivel() == null){
            throw new RuntimeException("La ruta ha de tener desnivel");
        }

        //Manejar usuario que la ha creado
        //Manejar Si tiene Fauna/Flora
        //Manejar municipio al que pertenece
        //Manejar coordenadas
        //No hace falta manejar comentarios ya que se acaba de crear y no tiene

        return rutaRepository.save(object);
    }

    @Override
    public boolean update(Ruta object) {

        if(object != null && object.getId() != null) {

            Ruta ruta = rutaRepository.findById(object.getId()).orElse(null);

            if(ruta == null){
                throw new RuntimeException("No existe la ruta " +object);
                //TODO: cambiarlo por un false ?
            }

            if(object.getNombre() != null){
                ruta.setNombre(object.getNombre());
            }

            if(object.getDificultad() != null){
                ruta.setDificultad(object.getDificultad());
            }

            if(object.getTiempoDuracion() != null){
                ruta.setTiempoDuracion(object.getTiempoDuracion());
            }

            if(object.getDistanciaMetros() != null){
                ruta.setDistanciaMetros(object.getDistanciaMetros());
            }

            if(object.getDesnivel() != null){
                ruta.setDesnivel(object.getDesnivel());
            }

            if(object.getAprobada() != null){
                ruta.setAprobada(object.getAprobada());
            }

            /*if(object.getUsuario() != null){
                ruta.setUsuario(object.getUsuario());
            }*/

            //Manejar si tiene comentarios
            //Manejar si tiene fauna/flora
            //Manejar si tiene municipio
            //Manejar si tiene coordenada

            rutaRepository.save(ruta);

            return true;
        } else{
            return false;
        }
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
