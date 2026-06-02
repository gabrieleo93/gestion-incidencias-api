package com.gestionincidencias.api.dto;

import com.gestionincidencias.api.enums.PrioridadIncidencia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidenciaRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 120, message = "El título no puede superar los 120 caracteres")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "La prioridad es obligatoria")
    private PrioridadIncidencia prioridad;

    @NotNull(message = "El usuario creador es obligatorio")
    private Integer usuarioCreadorId;
}