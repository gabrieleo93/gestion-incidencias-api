package com.gestionincidencias.api.controllers;

import com.gestionincidencias.api.dto.UsuarioRequestDTO;
import com.gestionincidencias.api.dto.UsuarioResponseDTO;
import com.gestionincidencias.api.services.UsuarioService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public UsuarioResponseDTO crearUsuario(@Valid @RequestBody UsuarioRequestDTO request) {
        return usuarioService.crearUsuario(request);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarUsuarioPorId(@PathVariable Integer id) {
        return usuarioService.buscarUsuarioPorId(id);
    }
}