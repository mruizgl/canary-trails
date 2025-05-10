package es.iespuertodelacruz.mp.canarytrails.controller;

import es.iespuertodelacruz.mp.canarytrails.entities.Coordenada;
import es.iespuertodelacruz.mp.canarytrails.service.CoordenadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import es.iespuertodelacruz.mp.canarytrails.dto.CoordenadaDTO;
import es.iespuertodelacruz.mp.canarytrails.mapper.CoordenadaMapper;


@RestController
@RequestMapping("/api/v1/admin/coordenadas")
@CrossOrigin(origins = "*")
public class CoordenadaController {

    @Autowired
    private CoordenadaService coordenadaService;

    @Autowired
    private CoordenadaMapper coordenadaMapper;

    @GetMapping
    public List<CoordenadaDTO> getAll() {
        return coordenadaMapper.toDTOList(coordenadaService.findAll());
    }

    @GetMapping("/{id}")
    public Optional<CoordenadaDTO> getById(@PathVariable Integer id) {
        return coordenadaService.findById(id)
                .map(coordenadaMapper::toDTO);
    }

    @PostMapping
    public CoordenadaDTO create(@RequestBody CoordenadaDTO dto) {
        Coordenada coordenada = coordenadaMapper.toEntity(dto);
        return coordenadaMapper.toDTO(coordenadaService.save(coordenada));
    }

    @PutMapping("/{id}")
    public CoordenadaDTO update(@PathVariable Integer id, @RequestBody CoordenadaDTO dto) {
        Coordenada coordenada = coordenadaMapper.toEntity(dto);
        coordenada.setId(id);
        return coordenadaMapper.toDTO(coordenadaService.save(coordenada));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        coordenadaService.deleteById(id);
    }

    @PostMapping("/lote")
    public List<CoordenadaDTO> createBatch(@RequestBody List<CoordenadaDTO> coordenadasDTO) {
        List<Coordenada> coordenadas = coordenadaMapper.toEntityList(coordenadasDTO);
        List<Coordenada> saved = coordenadaService.saveAll(coordenadas);
        return coordenadaMapper.toDTOList(saved);
    }
}