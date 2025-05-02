package es.iespuertodelacruz.mp.canarytrails.controller;

import es.iespuertodelacruz.mp.canarytrails.dto.FloraDTO;
import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
import es.iespuertodelacruz.mp.canarytrails.mapper.FloraMapper;
import es.iespuertodelacruz.mp.canarytrails.service.FloraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/flora")
@CrossOrigin(origins = "*")
public class FloraController {

    @Autowired
    private FloraService floraService;

    @Autowired
    private FloraMapper floraMapper;

    @GetMapping
    public List<FloraDTO> getAll() {
        return floraMapper.toDTOList(floraService.findAll());
    }

    @GetMapping("/{id}")
    public Optional<FloraDTO> getById(@PathVariable Integer id) {
        return floraService.findById(id)
                .map(floraMapper::toDTO);
    }

    @PostMapping
    public FloraDTO create(@RequestBody FloraDTO dto) {
        Flora flora = floraMapper.toEntity(dto);
        return floraMapper.toDTO(floraService.save(flora));
    }

    @PutMapping("/{id}")
    public FloraDTO update(@PathVariable Integer id, @RequestBody FloraDTO dto) {
        Flora flora = floraMapper.toEntity(dto);
        flora.setId(id);
        return floraMapper.toDTO(floraService.save(flora));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        floraService.deleteById(id);
    }
}

