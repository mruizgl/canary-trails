package es.iespuertodelacruz.mp.canarytrails.service;


import es.iespuertodelacruz.mp.canarytrails.common.ICrudService;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import es.iespuertodelacruz.mp.canarytrails.mapper.ComentarioMapper;
import es.iespuertodelacruz.mp.canarytrails.repository.ComentarioRepository;
import es.iespuertodelacruz.mp.canarytrails.repository.RutaRepository;
import es.iespuertodelacruz.mp.canarytrails.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComentarioService implements ICrudService<Comentario, Integer> {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private ComentarioMapper comentarioMapper;

    @Override
    public List<Comentario> findAll() {
        return comentarioRepository.findAll();
    }

    @Override
    public Optional<Comentario> findById(Integer id) {
        return comentarioRepository.findById(id);
    }

    @Override
    public Comentario save(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @Override
    public void deleteById(Integer id) {
        comentarioRepository.deleteById(id);
    }

    public ComentarioSalidaDto saveEntrada(ComentarioEntradaDto dto) {
        Comentario comentario = comentarioMapper.toEntity(dto);
        comentario.setUsuario(usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        comentario.setRuta(rutaRepository.findById(dto.rutaId())
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada")));
        return comentarioMapper.toDto(comentarioRepository.save(comentario));
    }


    public ComentarioSalidaDto update(Integer comentarioId, ComentarioEntradaDto dto, Integer usuarioId, boolean isAdmin) {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        if (!isAdmin && !comentario.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("No puedes modificar este comentario");
        }

        comentario.setTitulo(dto.titulo());
        comentario.setDescripcion(dto.descripcion());
        return comentarioMapper.toDto(comentarioRepository.save(comentario));
    }


    public ComentarioSalidaDto findSalidaById(Integer id) {
        return comentarioRepository.findById(id)
                .map(comentarioMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));
    }

    public List<ComentarioSalidaDto> findAllSalida() {
        return comentarioRepository.findAll().stream()
                .map(comentarioMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ComentarioSalidaDto> findByUsuarioId(Integer usuarioId) {
        return comentarioRepository.findByUsuarioId(usuarioId).stream()
                .map(comentarioMapper::toDto)
                .collect(Collectors.toList());
    }
}

