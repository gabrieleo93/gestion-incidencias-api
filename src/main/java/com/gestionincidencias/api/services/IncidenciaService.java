package com.gestionincidencias.api.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gestionincidencias.api.dto.IncidenciaRequestDTO;
import com.gestionincidencias.api.dto.IncidenciaResponseDTO;
import com.gestionincidencias.api.entities.Incidencia;
import com.gestionincidencias.api.entities.Usuario;
import com.gestionincidencias.api.enums.EstadoIncidencia;
import com.gestionincidencias.api.enums.PrioridadIncidencia;
import com.gestionincidencias.api.exceptions.BadRequestException;
import com.gestionincidencias.api.exceptions.NotFoundException;
import com.gestionincidencias.api.mappers.IncidenciaMapper;
import com.gestionincidencias.api.repositories.IncidenciaRepository;
import com.gestionincidencias.api.repositories.UsuarioRepository;

@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;
    private final IncidenciaMapper incidenciaMapper;

    public IncidenciaService(
            IncidenciaRepository incidenciaRepository,
            UsuarioRepository usuarioRepository,
            IncidenciaMapper incidenciaMapper
    ) {
        this.incidenciaRepository = incidenciaRepository;
        this.usuarioRepository = usuarioRepository;
        this.incidenciaMapper = incidenciaMapper;
    }

    public IncidenciaResponseDTO crearIncidencia(IncidenciaRequestDTO request) {

        Usuario usuarioCreador = usuarioRepository.findById(request.getUsuarioCreadorId())
                .orElseThrow(() -> new NotFoundException(
                        "Usuario creador no encontrado con ID: " + request.getUsuarioCreadorId()
                ));

        Incidencia incidencia = Incidencia.builder()
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .prioridad(request.getPrioridad())
                .usuarioCreador(usuarioCreador)
                .build();

        Incidencia nuevaIncidencia = incidenciaRepository.save(incidencia);

        return incidenciaMapper.toResponseDTO(nuevaIncidencia);
    }

    public List<IncidenciaResponseDTO> listarIncidencias() {
        return incidenciaRepository.findAll()
                .stream()
                .map(incidenciaMapper::toResponseDTO)
                .toList();
    }

    public IncidenciaResponseDTO buscarIncidenciaPorId(Integer id) {
        Incidencia incidencia = obtenerIncidenciaPorId(id);

        return incidenciaMapper.toResponseDTO(incidencia);
    }

    public List<IncidenciaResponseDTO> listarIncidenciasPorUsuario(Integer usuarioId) {
        return incidenciaRepository.findByUsuarioCreador_Id(usuarioId)
                .stream()
                .map(incidenciaMapper::toResponseDTO)
                .toList();
    }

    public List<IncidenciaResponseDTO> listarIncidenciasPorEstado(EstadoIncidencia estado) {
        return incidenciaRepository.findByEstado(estado)
                .stream()
                .map(incidenciaMapper::toResponseDTO)
                .toList();
    }

    public List<IncidenciaResponseDTO> listarIncidenciasPorPrioridad(PrioridadIncidencia prioridad) {
        return incidenciaRepository.findByPrioridad(prioridad)
                .stream()
                .map(incidenciaMapper::toResponseDTO)
                .toList();
    }

    public IncidenciaResponseDTO actualizarEstado(Integer id, EstadoIncidencia nuevoEstado) {
    Incidencia incidencia = obtenerIncidenciaPorId(id);

    if (incidencia.getEstado() == EstadoIncidencia.CERRADA) {
        throw new BadRequestException(
                "No se puede modificar una incidencia cerrada"
        );
    }

    incidencia.setEstado(nuevoEstado);
    incidencia.setFechaActualizacion(LocalDateTime.now());

    if (nuevoEstado == EstadoIncidencia.CERRADA || nuevoEstado == EstadoIncidencia.RESUELTA) {
        incidencia.setFechaCierre(LocalDateTime.now());
    }

    Incidencia incidenciaActualizada = incidenciaRepository.save(incidencia);

    return incidenciaMapper.toResponseDTO(incidenciaActualizada);
}

    public IncidenciaResponseDTO actualizarPrioridad(Integer id, PrioridadIncidencia nuevaPrioridad) {
    Incidencia incidencia = obtenerIncidenciaPorId(id);

    if (incidencia.getEstado() == EstadoIncidencia.RESUELTA ||
            incidencia.getEstado() == EstadoIncidencia.CERRADA) {
        throw new BadRequestException(
                "No se puede cambiar la prioridad de una incidencia resuelta o cerrada"
        );
    }

    incidencia.setPrioridad(nuevaPrioridad);
    incidencia.setFechaActualizacion(LocalDateTime.now());

    Incidencia incidenciaActualizada = incidenciaRepository.save(incidencia);

    return incidenciaMapper.toResponseDTO(incidenciaActualizada);
}

    private Incidencia obtenerIncidenciaPorId(Integer id) {
        return incidenciaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Incidencia no encontrada con ID: " + id
                ));
    }
}