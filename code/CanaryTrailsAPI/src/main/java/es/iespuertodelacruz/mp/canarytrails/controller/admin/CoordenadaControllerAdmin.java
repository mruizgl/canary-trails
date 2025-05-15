package es.iespuertodelacruz.mp.canarytrails.controller.admin;

import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.coordenada.CoordenadaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.service.CoordenadaService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.iespuertodelacruz.mp.canarytrails.mapper.CoordenadaMapper;


@RestController
@RequestMapping("/api/v1/admin/coordenadas")
@CrossOrigin(origins = "*")
public class CoordenadaControllerAdmin {

    @Autowired
    CoordenadaService coordenadaService;

    @Autowired
    CoordenadaMapper coordenadaMapper;

    @Autowired
    RutaService rutaService;

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

       /* for( int id : dto.rutas()){
            Ruta ruta = rutaService.findById(id);
            if(ruta != null && !coordenada.getRutas().contains(ruta)){
                coordenada.getRutas().add(ruta);
            }
        }*/

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

        /*if(dto.rutas() != null){
            for( int id : dto.rutas()){
                Ruta ruta = rutaService.findById(id);
                if(ruta != null){
                    coordenada.getRutas().add(ruta);
                }
            }
        }*/
        return ResponseEntity.ok(coordenadaService.update(coordenada));
    }

    /**
     * Endpoint que borra una coordenada de la bbdd segun la id
     * @param id de la coordenada a borrar
     * @return true si se ha borrado correctamente y false si no
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(coordenadaService.deleteById(id));
    }

    /*@PostMapping("/lote")
    public List<CoordenadaDTO> createBatch(@RequestBody List<CoordenadaDTO> coordenadasDTO) {
        List<Coordenada> coordenadas = coordenadaMapper.toEntityList(coordenadasDTO);
        List<Coordenada> saved = coordenadaService.saveAll(coordenadas);
        return coordenadaMapper.toDTOList(saved);
    }*/
}