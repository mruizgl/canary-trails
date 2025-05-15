package es.iespuertodelacruz.mp.canarytrails.controller.v2;

import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.fauna.FaunaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import es.iespuertodelacruz.mp.canarytrails.entities.Fauna;
import es.iespuertodelacruz.mp.canarytrails.entities.Ruta;
import es.iespuertodelacruz.mp.canarytrails.entities.Usuario;
import es.iespuertodelacruz.mp.canarytrails.mapper.FaunaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.FaunaService;
import es.iespuertodelacruz.mp.canarytrails.service.FotoManagementService;
import es.iespuertodelacruz.mp.canarytrails.service.RutaService;
import es.iespuertodelacruz.mp.canarytrails.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/faunas")
@CrossOrigin
public class FaunaControllerV2 {

    
    @Autowired
    FaunaService faunaService;

    @Autowired
    FaunaMapper faunaMapper;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RutaService rutaService;

    @Autowired
    FotoManagementService fotoManagementService;

    /**
     * Endpoint para obtener todas las faunas
     * @return una lista de todas las faunas con sus respectivas relaciones
     */
    @GetMapping
    public ResponseEntity<?> findAllFaunas(){
        //System.out.println("holaaaaaa estoy aqui");

        List<Fauna> faunas = faunaService.findAll();
        List<FaunaSalidaDto> faunaSalidasDto = faunas.stream()
                .map(faunaMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(faunaSalidasDto);
    }

    /**
     * Endpoint para obtener una fauna a partir de su id
     * @param id de la fauna que se quiere obtener
     * @return la fauna cuyo id coincide con el introducido
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findFaunaById(@PathVariable Integer id){

        Fauna fauna = faunaService.findById(id);

        if(fauna == null){
            return ResponseEntity.notFound().build();
        }

        FaunaSalidaDto dto = faunaMapper.toDto(fauna);

        return ResponseEntity.ok(dto);
    }

    /**
     * Endpoint para añadir una fauna a la bbdd
     * @param dto con los datos que se deben introducir para crear la fauna
     * @return un JSON de la fauna guardada
     */
    @PostMapping("/add")
    public ResponseEntity<?> createFauna(@RequestBody FaunaEntradaCreateDto dto){

        Fauna fauna = faunaMapper.toEntityCreate(dto);
        fauna.setAprobada(false);

        Usuario usuario = usuarioService.findById(dto.usuario());
        fauna.setUsuario(usuario);

        for( int id : dto.rutas()){
            Ruta ruta = rutaService.findById(id);
            if(ruta != null && !fauna.getRutas().contains(ruta)){
                fauna.getRutas().add(ruta);
            }
        }

        try{
            fauna = faunaService.save(fauna);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(faunaMapper.toDto(fauna));
    }

    /**
     * Endpoint que actualiza una fauna en la bbdd
     * @param dto con el objeto a actualizar, tiene que contener la id del objeto
     * @return true si se ha actualizado y false si no
     */
    @PutMapping("/update")
    public ResponseEntity<?> updateFauna(@RequestBody FaunaEntradaUpdateDto dto){

        //TODO: Comprobar si es el creador y si no está aprobada
        Fauna fauna = faunaMapper.toEntityUpdate(dto);
        fauna.setAprobada(false);

        if(dto.rutas() != null){
            for( int id : dto.rutas()){
                Ruta ruta = rutaService.findById(id);
                if(ruta != null && !fauna.getRutas().contains(ruta)){
                    fauna.getRutas().add(ruta);
                }
            }
        }

        boolean actualizada;

        try {
            actualizada = faunaService.update(fauna);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(actualizada);
    }

    /**
     * Endpoint que actualiza la foto de una fauna
     * @param id de la fauna a la que se le quiere colocar la foto
     * @param file foto que se le quiere establecer
     * @return la ruta que se le ha establecido a la fauna
     */
    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("id") Integer id, @RequestParam("file") MultipartFile file) {

        //TODO: comprobar si es el creador y si no está aprobada

        String mensaje = "";
        String categoria = "fauna";

        try {
            String namefile = fotoManagementService.save(file, categoria);
            mensaje = "" + namefile;

            Fauna fauna = faunaService.findById(id);

            fauna.setFoto(namefile);
            faunaService.update(fauna);

            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            mensaje = "No se pudo subir el archivo: " + file.getOriginalFilename()
                    + ". Error: " + e.getMessage();
            return ResponseEntity.badRequest().body(mensaje);
        }
    }


    /**
     * Borra una fauna a partir de la id
     * @param id de la fauna a borrar
     * @return true si se ha borrado, false si no
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFauna(@PathVariable("id") int id){

        //TODO: comprobar si ha sido creada por el usuario que quiere borrarla

        return ResponseEntity.ok(faunaService.deleteById(id));
    }

    public boolean esPropietario(Comentario comentario) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return comentario.getUsuario().getNombre().equals(username);
    }
}
