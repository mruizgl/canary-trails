package es.iespuertodelacruz.mp.canarytrails.service;

import es.iespuertodelacruz.mp.canarytrails.common.ICrudService;
import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.repository.MunicipioRepository;
import es.iespuertodelacruz.mp.canarytrails.repository.ZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ZonaService implements IServiceGeneric<Zona, Integer> {


    @Autowired
    private ZonaRepository zonaRepository;

    /*@Autowired
    private MunicipioRepository municipioRepository;*/

    @Override
    public List<Zona> findAll() {
        return zonaRepository.findAll();
    }

    @Override
    public Zona findById(Integer id) {
        return zonaRepository.findById(id).orElse(null);
    }

    @Override
    public Zona save(Zona zona) {

        if(zona.getNombre() == null){
            throw new RuntimeException("La zona tiene que tener nombre");
        }

        return zonaRepository.save(zona);
    }

    @Override
    public boolean update(Zona object) {
        if(object != null && object.getId() != null) {

            Zona zona = zonaRepository.findById(object.getId()).orElse(null);

            if(zona == null){
                throw new RuntimeException("No existe la zona " +object);
                //TODO: cambiarlo por un false ?
            }

            if(object.getNombre() != null){
                zona.setNombre(object.getNombre());
            }

            if(object.getMunicipios() != null){
                zona.setMunicipios(object.getMunicipios());
            }

            zonaRepository.save(zona);

            return true;
        } else{
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Integer id) {

        Zona zona = findById(id);

        if(!zona.getMunicipios().isEmpty()){
            throw new RuntimeException("La zona a eliminar no puede contener municipios");
        }

        int cantidad = zonaRepository.deleteZonaById(id);
        return cantidad>0;
    }

    /*public void eliminarZonaPorId(int zonaId) {
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
    }*/

}
