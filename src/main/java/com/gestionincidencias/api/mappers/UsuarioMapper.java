package com.gestionincidencias.api.mappers;

import org.springframework.stereotype.Component;

import com.gestionincidencias.api.dto.UsuarioResponseDTO;
import com.gestionincidencias.api.entities.Usuario;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .rol(usuario.getRol())
                .estado(usuario.getEstado())
                .fechaRegistro(usuario.getFechaRegistro())
                .build();
    }
}
