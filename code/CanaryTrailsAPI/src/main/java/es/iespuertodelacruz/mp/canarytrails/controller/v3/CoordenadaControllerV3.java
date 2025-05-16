package es.iespuertodelacruz.mp.canarytrails.controller.v3;

import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import es.iespuertodelacruz.mp.canarytrails.service.CoordenadaService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.iespuertodelacruz.mp.canarytrails.mapper.CoordenadaMapper;


@RestController
@RequestMapping("/api/v3/coordenadas")
@CrossOrigin(origins = "*")
public class CoordenadaControllerV3 {

    @Autowired
    CoordenadaService coordenadaService;

    @Autowired
    CoordenadaMapper coordenadaMapper;


    /**
     * Endpoint que devuelve todas las coordenadas de la bbdd
     * @return todas las coordenadas existentes
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(coordenadaMapper.toDTOList(coordenadaService.findAll()));
    }

    /**
     * Endpoint que devuelve una coordenada de la bbdd según la id
     * @param id de la coordenada
     * @return coordenada que tiene la id introducida
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Coordenada coordenada = coordenadaService.findById(id);

        if(coordenada == null){
            return ResponseEntity.notFound().build();
        }

        CoordenadaSalidaDto dto = coordenadaMapper.toDTO(coordenada);

        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint que crea una coordenada en la bbdd
     * @param dto con los datos de la coordenada a crear
     * @return la coordenada creada
     */
    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody CoordenadaEntradaCreateDto dto) {

        Coordenada coordenada = coordenadaMapper.toEntityCreate(dto);

        try{
            coordenada = coordenadaService.save(coordenada);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(coordenadaMapper.toDTO(coordenada));
    }

    /**
     * Endpoint que actualiza una coordenada de la bbdd
     * @param dto con los datos de la coordenada a actualizar
     * @return true si se ha actualizado correctamente o false si no
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CoordenadaEntradaUpdateDto dto) {
        Coordenada coordenada = coordenadaMapper.toEntityUpdate(dto);

        try{
            return ResponseEntity.ok(coordenadaService.update(coordenada));
        } catch (Error e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    /**
     * Endpoint que borra una coordenada de la bbdd segun la id
     * @param id de la coordenada a borrar
     * @return true si se ha borrado correctamente y false si no
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        Coordenada coordenada = coordenadaService.findById(id);

        if(coordenada ==  null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(coordenadaService.deleteById(id));
    }
}