package com.gestionincidencias.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gestionincidencias.api.dto.UsuarioRequestDTO;
import com.gestionincidencias.api.dto.UsuarioResponseDTO;
import com.gestionincidencias.api.entities.Usuario;
import com.gestionincidencias.api.exceptions.BadRequestException;
import com.gestionincidencias.api.exceptions.NotFoundException;
import com.gestionincidencias.api.mappers.UsuarioMapper;
import com.gestionincidencias.api.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuarioMapper::toResponseDTO)
                .toList();
    }

    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO usuarioDTO) {

        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new BadRequestException("Ya existe un usuario registrado con ese email");
        }

        Usuario usuario = Usuario.builder()
                .nombre(usuarioDTO.getNombre())
                .apellido(usuarioDTO.getApellido())
                .email(usuarioDTO.getEmail())
                .password(usuarioDTO.getPassword())
                .build();

        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        return usuarioMapper.toResponseDTO(nuevoUsuario);
    }

    public UsuarioResponseDTO buscarUsuarioPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));

        return usuarioMapper.toResponseDTO(usuario);
    }
}