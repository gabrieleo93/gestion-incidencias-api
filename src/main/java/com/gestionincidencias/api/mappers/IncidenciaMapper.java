package com.gestionincidencias.api.mappers;

import org.springframework.stereotype.Component;

import com.gestionincidencias.api.dto.IncidenciaResponseDTO;
import com.gestionincidencias.api.entities.Incidencia;
import com.gestionincidencias.api.entities.Usuario;

@Component
public class IncidenciaMapper {

    public IncidenciaResponseDTO toResponseDTO(Incidencia incidencia) {
        if (incidencia == null) {
            return null;
        }

        return IncidenciaResponseDTO.builder()
                .id(incidencia.getId())
                .titulo(incidencia.getTitulo())
                .descripcion(incidencia.getDescripcion())
                .estado(incidencia.getEstado())
                .prioridad(incidencia.getPrioridad())
                .fechaCreacion(incidencia.getFechaCreacion())
                .fechaActualizacion(incidencia.getFechaActualizacion())
                .fechaCierre(incidencia.getFechaCierre())
                .usuarioCreadorId(incidencia.getUsuarioCreador().getId())
                .nombreUsuarioCreador(obtenerNombreUsuario(incidencia.getUsuarioCreador()))
                .build();
    }

    private String obtenerNombreUsuario(Usuario usuario) {
        if (usuario.getApellido() == null || usuario.getApellido().isBlank()) {
            return usuario.getNombre();
        }

        return usuario.getNombre() + " " + usuario.getApellido();
    }
}