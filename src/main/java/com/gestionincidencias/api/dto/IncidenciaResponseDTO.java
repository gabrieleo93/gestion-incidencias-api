package com.gestionincidencias.api.dto;

import java.time.LocalDateTime;

import com.gestionincidencias.api.enums.EstadoIncidencia;
import com.gestionincidencias.api.enums.PrioridadIncidencia;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidenciaResponseDTO {

    private Integer id;

    private String titulo;
    private String descripcion;

    private EstadoIncidencia estado;
    private PrioridadIncidencia prioridad;

    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private LocalDateTime fechaCierre;

    private Integer usuarioCreadorId;
    private String nombreUsuarioCreador;
}