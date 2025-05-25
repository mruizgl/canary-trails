package es.iespuertodelacruz.mp.canarytrails.controller.v2;

import es.iespuertodelacruz.mp.canarytrails.dto.ruta.CoordenadaEntradaCreate;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaEntradaUpdateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.ruta.RutaSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.*;
import es.iespuertodelacruz.mp.canarytrails.mapper.RutaMapper;
import es.iespuertodelacruz.mp.canarytrails.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/rutas")
@CrossOrigin
public class RutaControllerV2 {

    @Autowired
    RutaService rutaService;

    @Autowired
    RutaMapper rutaMapper;

    @Autowired
    FloraService floraService;

    @Autowired
    FaunaService faunaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    CoordenadaService coordenadaService;

    @Autowired
    MunicipioService municipioService;

    @Autowired
    FotoManagementService fotoManagementService;

    /**
     * Endpoint que devuelve todas las rutas de la bbdd
     * @return todas las rutas existentes
     */
    @GetMapping
    public ResponseEntity<?> findAllRutas(){

        List<Ruta> rutas = rutaService.findAll();
        List<RutaSalidaDto> rutasSalidasDto = rutas.stream()
                .map(rutaMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(rutasSalidasDto);
    }

    @GetMapping("/creador/{id}")
    public ResponseEntity<?> findRutasByUsuarioId(@PathVariable Integer id){

        List<Ruta> rutas = rutaService.findAllByCreadorId(id);
        List<RutaSalidaDto> rutasSalidasDto = rutas.stream()
                .map(rutaMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(rutasSalidasDto);
    }

    /**
     * Endpoint que devuelve una ruta segun la id
     * @param id de la ruta
     * @return la ruta que tiene el id introducido
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> findRutaById(@PathVariable Integer id){

        Ruta ruta = rutaService.findById(id);

        if(ruta == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(rutaMapper.toDto(ruta));
    }



    /**
     * Endpoint que crea una ruta en la bbdd
     * @param dto con los datos de la ruta a crear
     * @return la ruta creada
     */
    @PostMapping("/add")
    public ResponseEntity<?> createRuta(@RequestBody RutaEntradaCreateDto dto){

        Ruta ruta = rutaMapper.toEntityCreate(dto);
        ruta.setAprobada(false);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioService.findByUserName(username);
        ruta.setUsuario(usuario);

        for( int id : dto.faunas()){
            Fauna fauna = faunaService.findById(id);
            if(fauna != null && !ruta.getFaunas().contains(fauna)){
                ruta.getFaunas().add(fauna);
            }
        }

        for( int id : dto.floras()){
            Flora flora = floraService.findById(id);
            if(flora != null && !ruta.getFloras().contains(flora)){
                ruta.getFloras().add(flora);
            }
        }

        /*List<Coordenada> coordenadas = new ArrayList<>();*/
        for( CoordenadaEntradaCreate coordCreate : dto.coordenadas()){

            Coordenada coordenada = coordenadaService.findByData(coordCreate.latitud(), coordCreate.longitud());

            if (coordenada == null) {

                coordenada = new Coordenada();
                coordenada.setLatitud(coordCreate.latitud());
                coordenada.setLongitud(coordCreate.longitud());

                coordenada = coordenadaService.save(coordenada); // guarda en la BBDD
            }

            if (!ruta.getCoordenadas().contains(coordenada)) {
                ruta.getCoordenadas().add(coordenada);
            }
        }

        for( int id : dto.municipios()){
            Municipio municipio = municipioService.findById(id);
            if(municipio != null && !ruta.getMunicipios().contains(municipio)){
                ruta.getMunicipios().add(municipio);
            }
        }

        try{
            ruta = rutaService.save(ruta);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok(rutaMapper.toDto(ruta));
    }

    @PostMapping(value = "/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("id") Integer id, @RequestParam("file") MultipartFile file) {

        String mensaje = "";
        String categoria = "ruta";

        try {
            String namefile = fotoManagementService.save(file, categoria);
            mensaje = "" + namefile;

            Ruta ruta = rutaService.findById(id);

            if(ruta == null){
                return ResponseEntity.notFound().build();
            }

            if(!esPropietario(ruta) || ruta.getAprobada()){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es el creador de la ruta o la ruta ya ha sido aprobada");
            }


            if(!ruta.getFotos().contains(namefile)){
                ruta.getFotos().add(namefile);
            } else {
                String mensajeFotoDuplicada = "La foto ya existe en esta ruta";
                return ResponseEntity.badRequest().body(mensajeFotoDuplicada);
            }

            rutaService.uploadFotoRuta(ruta);

            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            mensaje = "No se pudo subir el archivo: " + file.getOriginalFilename()
                    + ". Error: " + e.getMessage();
            return ResponseEntity.badRequest().body(mensaje);
        }
    }

    /**
     * Endpoint que borra una ruta de la bbdd a partir de su id
     * @param id de la ruta a borrar
     * @return true si se ha borrado correctamente o false si no
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRuta(@PathVariable("id") int id){

        if(rutaService.findById(id) == null){
            return ResponseEntity.notFound().build();
        }

        if(!esPropietario(rutaService.findById(id)) || rutaService.findById(id).getAprobada()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No es el creador de la ruta o la ruta ya ha sido aprobada");
        }

        return ResponseEntity.ok(rutaService.deleteById(id));
    }

    public boolean esPropietario(Ruta ruta) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("EEEEY: "+ruta.getUsuario().getNombre()+ " ---- " +username);
        return ruta.getUsuario().getNombre().equals(username);
    }
}
