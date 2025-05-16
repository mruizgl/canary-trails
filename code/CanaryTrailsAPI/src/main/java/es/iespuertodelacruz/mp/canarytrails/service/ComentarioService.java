package es.iespuertodelacruz.mp.canarytrails.service;


import es.iespuertodelacruz.mp.canarytrails.common.IServiceGeneric;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioEntradaCreateDto;
import es.iespuertodelacruz.mp.canarytrails.dto.comentario.ComentarioSalidaDto;
import es.iespuertodelacruz.mp.canarytrails.entities.Comentario;
import es.iespuertodelacruz.mp.canarytrails.entities.Zona;
import es.iespuertodelacruz.mp.canarytrails.mapper.ComentarioMapper;
import es.iespuertodelacruz.mp.canarytrails.repository.ComentarioRepository;
import es.iespuertodelacruz.mp.canarytrails.repository.RutaRepository;
import es.iespuertodelacruz.mp.canarytrails.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioService implements IServiceGeneric<Comentario, Integer> {

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
    public Comentario findById(Integer id) {
        return comentarioRepository.findById(id).orElse(null);
    }

    @Override
    public Comentario save(Comentario comentario) {

        if(comentario.getTitulo() == null){
            throw new RuntimeException("El comentario ha de tener titulo");
        }

        if(comentario.getDescripcion() == null){
            throw new RuntimeException("El comentario debe contener una descripcion");
        }

        if(comentario.getUsuario() == null){
            throw new RuntimeException("El comentario debe tener asignado un usuario escritor");
        }

        if(comentario.getRuta() == null){
            throw new RuntimeException("El comentario debe tener una ruta donde fue escrito");
        }

        return comentarioRepository.save(comentario);
    }

    @Override
    public boolean update(Comentario object) {
        if(object != null && object.getId() != null) {

            Comentario comentario = comentarioRepository.findById(object.getId()).orElse(null);

            if(comentario == null){
                throw new RuntimeException("No existe el comentario " +object);
            }

            if(object.getTitulo() != null){
                comentario.setTitulo(object.getTitulo());
            }

            if(object.getDescripcion() != null){
                comentario.setDescripcion(object.getDescripcion());
            }

            if(object.getUsuario() != null){
                comentario.setUsuario(object.getUsuario());
            }

            if(object.getRuta() != null){
                comentario.setRuta(object.getRuta());
            }

            comentarioRepository.save(comentario);

            return true;
        } else{
            return false;
        }
    }

    @Override
    @Transactional
    public boolean deleteById(Integer id) {
       int cantidad = comentarioRepository.deleteComentarioById(id);

       return cantidad>0;
    }

    /*public ComentarioSalidaDto saveEntrada(ComentarioEntradaCreateDto dto) {
        Comentario comentario = comentarioMapper.toEntity(dto);
        comentario.setUsuario(usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado")));
        comentario.setRuta(rutaRepository.findById(dto.rutaId())
                .orElseThrow(() -> new RuntimeException("Ruta no encontrada")));
        return comentarioMapper.toDto(comentarioRepository.save(comentario));
    }


    public ComentarioSalidaDto update(Integer comentarioId, ComentarioEntradaCreateDto dto, Integer usuarioId, boolean isAdmin) {
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new RuntimeException("Comentario no encontrado"));

        if (!isAdmin && !comentario.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("No puedes modificar este comentario");
        }

        comentario.setTitulo(dto.titulo());
        comentario.setDescripcion(dto.descripcion());
        return comentarioMapper.toDto(comentarioRepository.save(comentario));
    }*/


   /* public ComentarioSalidaDto findSalidaById(Integer id) {
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
    }*/
}

