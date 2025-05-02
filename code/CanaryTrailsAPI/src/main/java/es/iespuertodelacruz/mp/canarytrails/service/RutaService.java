package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Transactional
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
    @Transactional
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
    @Transactional
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

    /**
     * Metodo para obtener las rutas favoritas a partir de la id de usuario
     * @param userId es la id del usuario que tiene esas rutas añadidas como favoritas
     * @return la lista de rutas favoritas de ese usuario
     */
    public List<Ruta> findRutasFavoritasByUserId(Integer userId){

        List<Integer> idsFavoritas = rutaRepository.findFavoritasByUserId(userId);
        List<Ruta> rutasFavoritas = rutaRepository.findAllById(idsFavoritas);

        // Verificación opcional de que no falte ninguna
        if (rutasFavoritas.size() != idsFavoritas.size()) {
            throw new RuntimeException("Alguna de las rutas favoritas no existe");
        }

        return rutasFavoritas;
    }

    @Transactional
    public boolean aniadirRutaFavorita(Integer usuarioId, Integer rutaId){

        int cantidad = rutaRepository.addFavoritaById(usuarioId, rutaId);

        return cantidad >0;
    }

    @Transactional
    public boolean deleteRutaFavorita(Integer usuarioId, Integer rutaId){

        int cantidad = rutaRepository.deleteFavoritaById(usuarioId, rutaId);

        return cantidad >0;
    }

}
