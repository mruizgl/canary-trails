package es.iespuertodelacruz.mp.canarytrails.controller.v3;

import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.municipio.MunicipioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Municipio;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.mapper.MunicipioMapper;
import es.iespuertodelacruz.mp.canarytrails.service.MunicipioService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.ZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v3/municipios")
@CrossOrigin(origins = "*")
public class MunicipioControllerV3 {

    @Autowired
    MunicipioService municipioService;

    @Autowired
    MunicipioMapper municipioMapper;

    @Autowired
    ZonaService zonaService;

    @Autowired
    RutaService rutaService;

    /**
     * Endpoint que devuelve una lista de todos los municipios
     * @return todos los municipios de la bbdd
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Municipio> municipios = municipioService.findAll();
        List<MunicipioSalidaDto> dtos = municipios.stream()
                .map(municipioMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * Endpoint que devuelve un municipio a partir de su id
     * @param id del municipio
     * @return municipio cuya id se ha introducido
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Municipio municipio = municipioService.findById(id);

        if (municipio == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(municipioMapper.toDto(municipio));
    }

    /**
     * Enpoint que crea un municipio en la bbdd
     * @param dto con los datos del municipio
     * @return el municipio creado
     */
    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody MunicipioEntradaCreateDto dto) {

        Municipio municipio = municipioMapper.toEntityCreate(dto);

        Zona zona = zonaService.findById(dto.zona());

        if(zona == null){
            return ResponseEntity.notFound().build();
        }

        municipio.setZona(zona);

        for( int id : dto.rutas()){
            Ruta ruta = rutaService.findById(id);
            if(ruta != null && !municipio.getRutas().contains(ruta)){
                municipio.getRutas().add(ruta);
            }
        }

        try{
           municipio = municipioService.save(municipio);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(municipioMapper.toDto(municipio));
    }

    /**
     * Endpoint que actualiza un municipio
     * @param dto con los datos a actualizar y la id del municipio actualizando
     * @return true si se ha actualizado false si no
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody MunicipioEntradaUpdateDto dto) {

        Municipio municipio = municipioMapper.toEntityUpdate(dto);
        Zona zona = zonaService.findById(dto.zona());

        if(zona == null){
            return ResponseEntity.notFound().build();
        }

        municipio.setZona(zona);

        if(dto.rutas() != null){
            for( int id : dto.rutas()){
                Ruta ruta = rutaService.findById(id);
                if(ruta != null && !municipio.getRutas().contains(ruta)){
                    municipio.getRutas().add(ruta);
                }
            }
        }

        try {
            return ResponseEntity.ok(municipioService.update(municipio));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Endpoint que elimina un municipio a partir de su id
     * @param id del municipio
     * @return true si se ha borrado, false si no
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        Municipio municipio = municipioService.findById(id);

        if(municipio == null){
            //Sin información para que no sepan si existe o no
            return ResponseEntity.notFound().build();
        }

        try{
            return ResponseEntity.ok(municipioService.deleteById(id));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
