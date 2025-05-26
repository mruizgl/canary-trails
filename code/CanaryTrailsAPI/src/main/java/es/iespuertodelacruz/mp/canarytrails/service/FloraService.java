package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.repository.FloraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FloraService implements IServiceGeneric<Flora, Integer> {

    @Autowired
    private FloraRepository floraRepository;

    @Override
    public List<Flora> findAll() {
        return floraRepository.findAll();
    }

    public List<Flora> findAllByCreadorId(Integer id){
        return floraRepository.findCreadasByUsuarioId(id);
    }

    @Override
    public Flora findById(Integer id) {
        return floraRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Flora save(Flora flora) {

        if(flora.getNombre() == null){
            throw new RuntimeException("El flora ha de tener nombre");
        }

        if(flora.getDescripcion() == null){
            throw new RuntimeException("La descripción ha de estar completa para crearla");
        }

        if(flora.getEspecie() == null){
            throw new RuntimeException("La especie no puede ser nula");
        }

        if(flora.getTipoHoja() == null){
            throw new RuntimeException("El tipo de hoja ha de estar establecido para crear");
        }

        if(flora.getSalidaFlor() == null){
            throw new RuntimeException("La salida de flor ha de estar establecida");
        }

        if(flora.getCaidaFlor() == null){
            throw new RuntimeException("La caida de flor ha de estar establecida");
        }

        if(flora.getUsuario() == null ){
            throw new RuntimeException("El usuario ha de existir");
        }

        //La foto se permite establecerse en nulo, en el front cargamos un "No tiene foto" o algo así

        Flora savedFlora = floraRepository.save(flora);

        if (savedFlora.getRutas() != null && !savedFlora.getRutas().isEmpty()) {
            for (Ruta ruta : savedFlora.getRutas()) {
                floraRepository.addRutaFloraRelation(savedFlora.getId(), ruta.getId());
            }
        }

        return savedFlora;
    }

    @Override
    @Transactional
    public boolean update(Flora object) {
        if(object != null && object.getId() != null) {

            Flora flora = floraRepository.findById(object.getId()).orElse(null);

            if(flora == null){
                throw new RuntimeException("No existe la flora " +object.getId());
                //TODO: cambiarlo por un false ?
            }

            if(object.getNombre() != null){
                flora.setNombre(object.getNombre());
            }

            if(object.getDescripcion() != null){
                flora.setDescripcion(object.getDescripcion());
            }

            if(object.getAprobada() != null){
                flora.setAprobada(object.getAprobada());
            }

            if(object.getUsuario() != null){
                flora.setUsuario(object.getUsuario());
            }

            if (object.getRutas() != null) {
                flora.setRutas(object.getRutas());
            }

            if (object.getFoto() != null && !object.getFoto().isBlank()) {
                flora.setFoto(object.getFoto());
            }

            Flora savedFlora = floraRepository.save(flora);

            // Se borran las relaciones siempre. Si hay nuevas se actualizan, si no se quiere actualizar se tienen q
            // poner las id de las que ya estaban, y si no se pone ninguna o un 0, se borran todas las relaciones
            int cantidad = floraRepository.deleteRutaFloraRelation(savedFlora.getId());
            //System.out.println("HEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEY"+cantidad);

            if (savedFlora.getRutas() != null && !savedFlora.getRutas().isEmpty()) {
                for (Ruta ruta : object.getRutas()) {
                    floraRepository.addRutaFloraRelation(savedFlora.getId(), ruta.getId());
                }
            }

            return true;
        } else{
            return false;
        }
    }

    @Transactional
    public boolean uploadFoto(Flora flora){
        floraRepository.save(flora);
        return true;
    }

    // Borra a partir de la id recibida. Devuelve el numero de lineas modificadas
    // Si se ha borrado alguna, es un true, si no es un false
    @Override
    @Transactional
    public boolean deleteById(Integer id) {

        int cantidad = floraRepository.deleteFloraById(id);

        return cantidad>0;
    }
}
