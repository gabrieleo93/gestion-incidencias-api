package com.gestionincidencias.api.dto;
import com.gestionincidencias.api.enums.RolUsuario;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {

    private String token;
    private String tipo;
    private Integer usuarioId;
    private String nombre;
    private String email;
    private RolUsuario rol;
}