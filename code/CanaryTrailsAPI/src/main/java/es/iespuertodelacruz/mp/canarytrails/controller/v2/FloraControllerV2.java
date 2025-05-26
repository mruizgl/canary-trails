package es.iespuertodelacruz.mp.canarytrails.controller.v2;

import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.flora.FloraEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.flora.FloraEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.flora.FloraSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.*;
import es.iespuertodelacruz.mp.canarytrails.mapper.FloraMapper;
import es.iespuertodelacruz.mp.canarytrails.service.FloraService;
import es.iespuertodelacruz.mp.canarytrails.service.FotoManagementService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Controlador REST para la gestión de la entidad Flora.
 * Proporciona endpoints para crear, leer, actualizar y eliminar registros de flora.
 * @author Melissa R.G. y Pedro M.E.
 */
@RestController
@RequestMapping("/api/v2/floras")
@CrossOrigin(origins = "*")
public class FloraControllerV2 {

    @Autowired
    FloraService floraService;

    @Autowired
    FloraMapper floraMapper;

    @Autowired
    RutaService rutaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    FotoManagementService fotoManagementService;

    /**
     * Endpoint para obtener todas las floras
     * @return una lista de todas las floras con sus respectivas relaciones
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(floraMapper.toDTOList(floraService.findAll()));
    }

    @GetMapping("/creador/{id}")
    public ResponseEntity<?> findFaunasByUsuarioId(@PathVariable Integer id){

        return ResponseEntity.ok(floraMapper.toDTOList(floraService.findAllByCreadorId(id)));
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
        flora.setAprobada(false);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.findByUserName(username);
        flora.setUsuario(usuario);

        if(dto.rutas() != null){
            for( int id : dto.rutas()){
                Ruta ruta = rutaService.findById(id);
                if(ruta != null && !flora.getRutas().contains(ruta)){
                    flora.getRutas().add(ruta);
                }
            }
        }

        try{
            flora = floraService.save(flora);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(floraMapper.toDTO(flora));
    }

    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("id") Integer id, @RequestParam("file") MultipartFile file) {

        String mensaje = "";
        String categoria = "flora";

        try {
            String namefile = fotoManagementService.save(file, categoria);
            mensaje = "" + namefile;

            Flora flora = floraService.findById(id);

            if(flora == null){
                return ResponseEntity.notFound().build();
            }

            if(!esPropietario(flora) || flora.getAprobada()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es el creador de la flora o esta ya está aprobada");
            }

            flora.setFoto(namefile);
            floraService.uploadFoto(flora);

            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            mensaje = "No se pudo subir el archivo: " + file.getOriginalFilename()
                    + ". Error: " + e.getMessage();
            return ResponseEntity.badRequest().body(mensaje);
        }
    }

    /**
     * Borra una flora a partir de la id
     * @param id de la flora a borrar
     * @return true si se ha borrado, false si no
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {

        if(floraService.findById(id) == null){
            return ResponseEntity.notFound().build();
        }

        if(!esPropietario(floraService.findById(id)) || floraService.findById(id).getAprobada()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es el creador de la flora o esta ya está aprobada");
        }

        return ResponseEntity.ok(floraService.deleteById(id));
    }

    public boolean esPropietario(Flora flora) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return flora.getUsuario().getNombre().equals(username);
    }
}

