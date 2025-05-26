package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.entities.*;
import es.iespuertodelacruz.mp.canarytrails.repository.*;
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

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    FaunaRepository faunaRepository;

    @Autowired
    FloraRepository floraRepository;

    @Autowired
    ComentarioRepository comentarioRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    CoordenadaRepository coordenadaRepository;

    @Override
    public List<Ruta> findAll() {
        return rutaRepository.findAll();
    }

    public List<Ruta> findAllPopulares() {
        return rutaRepository.findRutasConMasDe10Favoritos();
    }

    public List<Ruta> findAllByCreadorId(Integer id){
        return rutaRepository.findCreadasByUsuarioId(id);
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

        if(object.getUsuario() == null){
            throw new RuntimeException("La ruta ha de tener un usuario creador");
        }

        if(object.getMunicipios() == null){
            throw new RuntimeException("La ruta ha de tener al menos un municipio asociado");
        }

        if(object.getCoordenadas() == null || object.getCoordenadas().size() < 2){
            throw new RuntimeException("La ruta ha de tener al menos dos coordenadas asociada");
        }

        Ruta guardada = rutaRepository.save(object);

        /*if (guardada.getFaunas() != null && !guardada.getFaunas().isEmpty()) {
            for (Fauna fauna : guardada.getFaunas()) {
                faunaRepository.addRutaFaunaRelation(fauna.getId(), guardada.getId());
            }
        }

        if (guardada.getFloras() != null && !guardada.getFloras().isEmpty()) {
            for (Flora flora : guardada.getFloras()) {
                floraRepository.addRutaFloraRelation(flora.getId(), guardada.getId());
            }
        }

        if (guardada.getCoordenadas() != null && !guardada.getCoordenadas().isEmpty()) {
            for (Coordenada coordenada : guardada.getCoordenadas()) {
                coordenadaRepository.addRutaCoordenadaRelation(coordenada.getId(), guardada.getId());
            }
        }*/

        /*if (guardada.getMunicipios() != null && !guardada.getMunicipios().isEmpty()) {
            for (Municipio municipio : guardada.getMunicipios()) {
                municipioRepository.addRutaMunicipioRelation(municipio.getId(), guardada.getId());
            }
        }*/

        //No hace falta manejar comentarios ya que se acaba de crear y no tiene

        return guardada;
    }

    @Override
    @Transactional
    public boolean update(Ruta object) {

        if(object != null && object.getId() != null) {

            Ruta ruta = rutaRepository.findById(object.getId()).orElse(null);

            if(ruta == null){
                throw new RuntimeException("No existe la ruta " +object);
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

            if(object.getUsuario() != null){
                ruta.setUsuario(object.getUsuario());
            }

            if(object.getFaunas() != null){
                ruta.setFaunas(object.getFaunas());
            }

            if(object.getFloras() != null){
                ruta.setFloras(object.getFloras());
            }

            if(object.getMunicipios() != null){
                ruta.setMunicipios(object.getMunicipios());
            }

            if(object.getCoordenadas() != null){
                ruta.setCoordenadas(object.getCoordenadas());
            }

            if(object.getFotos() != null && !object.getFotos().isEmpty()){
                ruta.setFotos(object.getFotos());
            }

            Ruta actualizada = rutaRepository.save(ruta);

            rutaRepository.deleteRutaFaunaRelation(actualizada.getId());
            rutaRepository.deleteRutaFloraRelation(actualizada.getId());
            rutaRepository.deleteRutaCoordenadaRelation(actualizada.getId());
            rutaRepository.deleteRutaMunicipioRelation(actualizada.getId());

            if (actualizada.getFaunas() != null && !actualizada.getFaunas().isEmpty()) {
                for (Fauna fauna : actualizada.getFaunas()) {
                    faunaRepository.addRutaFaunaRelation(fauna.getId(), actualizada.getId());
                }
            }

            if (actualizada.getFloras() != null && !actualizada.getFloras().isEmpty()) {
                for (Flora flora : actualizada.getFloras()) {
                    floraRepository.addRutaFloraRelation(flora.getId(), actualizada.getId());
                }
            }

            if (actualizada.getCoordenadas() != null && !actualizada.getCoordenadas().isEmpty()) {
                for (Coordenada coordenada : actualizada.getCoordenadas()) {
                    coordenadaRepository.addRutaCoordenadaRelation(coordenada.getId(), actualizada.getId());
                }
            }

            if (actualizada.getMunicipios() != null && !actualizada.getMunicipios().isEmpty()) {
                for (Municipio municipio : actualizada.getMunicipios()) {
                    municipioRepository.addRutaMunicipioRelation(municipio.getId(), actualizada.getId());
                }
            }

            return true;
        } else{
            return false;
        }
    }

    @Transactional
    public boolean uploadFotoRuta(Ruta ruta){
        rutaRepository.save(ruta);
        return true;
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

        //Poner una verificacion al borrar la ruta de que se van a borrar los comentarios
        comentarioRepository.deleteComentarioByRutaId(id);

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
