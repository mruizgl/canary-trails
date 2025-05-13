package es.iespuertodelacruz.mp.canarytrails.controller.admin;

import es.iespuertodelacruz.mp.canarytrails.dto.flora.FloraEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.flora.FloraEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.flora.FloraSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.FloraMapper;
import es.iespuertodelacruz.mp.canarytrails.service.FloraService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controlador REST para la gestión de la entidad Flora.
 * Proporciona endpoints para crear, leer, actualizar y eliminar registros de flora.
 * @author Melissa R.G. y Pedro M.E.
 */
@RestController
@RequestMapping("/api/v1/admin/floras")
@CrossOrigin(origins = "*")
public class FloraControllerAdmin {

    @Autowired
    FloraService floraService;

    @Autowired
    FloraMapper floraMapper;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RutaService rutaService;

    /**
     * Endpoint para obtener todas las floras
     * @return una lista de todas las floras con sus respectivas relaciones
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(floraMapper.toDTOList(floraService.findAll()));
    }

    /**
     * Endpoint para obtener una flora a partir de su id
     * @param id de la flora que se quiere obtener
     * @return la flora cuyo id coincide con el introducido
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {

        Flora flora = floraService.findById(id);

        if(flora == null){
            return ResponseEntity.notFound().build();
        }

        FloraSalidaDto dto = floraMapper.toDTO(flora);

        return ResponseEntity.ok(dto);
    }

    /**
     * Enpoint que crea una flora a partir de los datos introducidos
     * @param dto con los datos que ha de introducir el usuario
     * @return el JSON del objeto que se ha guardado en la bbdd
     */
    @PostMapping("/add")
    public ResponseEntity<?> create(@RequestBody FloraEntradaCreateDto dto) {

        Flora flora = floraMapper.toEntityCreate(dto);

        Usuario usuario = usuarioService.findById(dto.usuario());
        flora.setUsuario(usuario);

        for( int id : dto.rutas()){
            Ruta ruta = rutaService.findById(id);
            if(ruta != null && !flora.getRutas().contains(ruta)){
                flora.getRutas().add(ruta);
            }
        }

        try{
            flora = floraService.save(flora);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(floraMapper.toDTO(flora));
    }

    /**
     * Endpoint que actualiza una flora en la bbdd
     * @param dto con el objeto a actualizar, tiene que contener la id del objeto
     * @return true si se ha actualizado y false si no
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody FloraEntradaUpdateDto dto) {

        Flora flora = floraMapper.toEntityUpdate(dto);

        Usuario usuario = usuarioService.findById(dto.usuario());
        flora.setUsuario(usuario);

        if(dto.rutas() != null){
            for( int id : dto.rutas()){
                Ruta ruta = rutaService.findById(id);
                if(ruta != null && !flora.getRutas().contains(ruta)){
                    flora.getRutas().add(ruta);
                }
            }
        }

        boolean actualizada;

        try{
            actualizada = floraService.update(flora);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(actualizada);
    }

    /**
     * Borra una flora a partir de la id
     * @param id de la flora a borrar
     * @return true si se ha borrado, false si no
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        return ResponseEntity.ok(floraService.deleteById(id));
    }
}

