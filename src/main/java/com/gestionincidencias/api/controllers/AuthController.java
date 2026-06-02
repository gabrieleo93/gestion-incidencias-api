package com.gestionincidencias.api.controllers;

import org.springframework.web.bind.annotation.*;

import com.gestionincidencias.api.dto.AuthRequestDTO;
import com.gestionincidencias.api.dto.AuthResponseDTO;
import com.gestionincidencias.api.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody AuthRequestDTO request) {
        return authService.login(request);
    }
}