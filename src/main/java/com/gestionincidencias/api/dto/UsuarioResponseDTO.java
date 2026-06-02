package com.gestionincidencias.api.dto;

import java.time.LocalDateTime;

import com.gestionincidencias.api.enums.EstadoUsuario;
import com.gestionincidencias.api.enums.RolUsuario;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private String email;
    private RolUsuario rol;
    private EstadoUsuario estado;
    private LocalDateTime fechaRegistro;
}
