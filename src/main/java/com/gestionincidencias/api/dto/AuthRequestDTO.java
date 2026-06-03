package com.gestionincidencias.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthRequestDTO {
    @NotBlank(message ="El email es obligatorio")
    @Email(message = "El email no es valido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}
