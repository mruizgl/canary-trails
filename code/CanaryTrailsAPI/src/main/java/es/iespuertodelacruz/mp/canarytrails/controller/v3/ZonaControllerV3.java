package es.iespuertodelacruz.mp.canarytrails.controller.v3;

import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.zona.ZonaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.*;
import es.iespuertodelacruz.mp.canarytrails.mapper.ZonaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.MunicipioService;
import es.iespuertodelacruz.mp.canarytrails.service.ZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v3/zonas")
@CrossOrigin(origins = "*")
public class ZonaControllerV3 {

    @Autowired
    ZonaService zonaService;

    @Autowired
    ZonaMapper zonaMapper;

    @Autowired
    MunicipioService municipioService;

    /**
     * Endpoint que devuelve todas las zonas
     * @return todas las zonas existentes en la bbdd
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Zona> zonas = zonaService.findAll();
        List<ZonaSalidaDto> dtos = zonaMapper.toDTOList(zonas);
        return ResponseEntity.ok(dtos);
    }

    /**
     * Endpoint que devuelve una zona segun la id
     * @param id de la zona
     * @return la zona que tenga la id introducida
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Zona zona = zonaService.findById(id);

        if(zona == null){
            return ResponseEntity.notFound().build();
        }

        ZonaSalidaDto dto = zonaMapper.toDTO(zona);

        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint para crear una zona
     * @param dto con los datos de la zona a crear
     * @return la zona creada
     */
    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody ZonaEntradaCreateDto dto) {

        Zona zona = zonaMapper.toEntityCreate(dto);

        for( int id : dto.municipios()){
            Municipio municipio = municipioService.findById(id);
            if(municipio != null){
                //TODO: en el front hay que avisar si el municipio ya tiene asociada una zona
                municipio.setZona(zona);    //Hay que actualizar la relacion ya que Zona no es el dueño de la relacion
                zona.getMunicipios().add(municipio);
            }
        }

        try{
             zona = zonaService.save(zona);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(zonaMapper.toDTO(zona));
    }

    /**
     * Endpoint para actualizar los datos de una zona ya existente
     * @param dto con los datos de la zona a actualizar, incluida su id
     * @return true si se ha actualizado o false si no
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ZonaEntradaUpdateDto dto) {

        Zona zona = zonaMapper.toEntityUpdate(dto);

        if(dto.municipios() != null){
            for( int id : dto.municipios()){
                Municipio municipio = municipioService.findById(id);
                if(municipio != null){
                    municipio.setZona(zona);    //Hay que actualizar la relacion ya que Zona no es el dueño de la relacion
                    zona.getMunicipios().add(municipio);
                } else {
                    return ResponseEntity.badRequest().body(
                            "El municipio no existe o esta intentando eliminar una relación con municipio. " +
                            "No se puede dejar un Municipio sin Zona"
                    );
                }
            }
        }

        try {
            return ResponseEntity.ok(zonaService.update(zona));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint que elimina una zona segun su id. Si la zona contiene municipios, no deja eliminarla
     * @param id de la zona
     * @return true si se ha eliminado o false si no
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        Zona zona = zonaService.findById(id);

        if(zona == null){
            //Sin información para que no sepan si existe o no
            return ResponseEntity.notFound().build();
        }

        try{
            return ResponseEntity.ok(zonaService.deleteById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
