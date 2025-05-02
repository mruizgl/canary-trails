package es.iespuertodelacruz.mp.canarytrails.controller;

import es.iespuertodelacruz.mp.canarytrails.entities.Flora;
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

    @GetMapping
    public List<Flora> getAll() {
        return floraService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Flora> getById(@PathVariable Integer id) {
        return floraService.findById(id);
    }

    @PostMapping
    public Flora create(@RequestBody Flora flora) {
        return floraService.save(flora);
    }

    @PutMapping("/{id}")
    public Flora update(@PathVariable Integer id, @RequestBody Flora flora) {
        flora.setId(id);
        return floraService.save(flora);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        floraService.deleteById(id);
    }
}
