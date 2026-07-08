package com.gestionincidencias.api.controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.gestionincidencias.api.dto.IncidenciaRequestDTO;
import com.gestionincidencias.api.dto.IncidenciaResponseDTO;
import com.gestionincidencias.api.enums.EstadoIncidencia;
import com.gestionincidencias.api.enums.PrioridadIncidencia;
import com.gestionincidencias.api.services.IncidenciaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    @PostMapping
    public IncidenciaResponseDTO crearIncidencia(@Valid @RequestBody IncidenciaRequestDTO request) {
        return incidenciaService.crearIncidencia(request);
    }

    @GetMapping
    public List<IncidenciaResponseDTO> listarIncidencias() {
        return incidenciaService.listarIncidencias();
    }

    // Cambio funcional: permite que un USER vea únicamente sus propias incidencias.
    @GetMapping("/mis")
    public List<IncidenciaResponseDTO> listarMisIncidencias(Authentication authentication) {
        return incidenciaService.listarMisIncidencias(authentication.getName());
    }

    // Cambio funcional: permite que un USER abra el detalle solo de una incidencia propia.
    @GetMapping("/mis/{id}")
    public IncidenciaResponseDTO buscarMiIncidenciaPorId(
            @PathVariable Integer id,
            Authentication authentication
    ) {
        return incidenciaService.buscarMiIncidenciaPorId(id, authentication.getName());
    }

    @GetMapping("/{id}")
    public IncidenciaResponseDTO buscarIncidenciaPorId(@PathVariable Integer id) {
        return incidenciaService.buscarIncidenciaPorId(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<IncidenciaResponseDTO> listarIncidenciasPorUsuario(@PathVariable Integer usuarioId) {
        return incidenciaService.listarIncidenciasPorUsuario(usuarioId);
    }

    @GetMapping("/estado/{estado}")
    public List<IncidenciaResponseDTO> listarIncidenciasPorEstado(@PathVariable EstadoIncidencia estado) {
        return incidenciaService.listarIncidenciasPorEstado(estado);
    }

    @GetMapping("/prioridad/{prioridad}")
    public List<IncidenciaResponseDTO> listarIncidenciasPorPrioridad(@PathVariable PrioridadIncidencia prioridad) {
        return incidenciaService.listarIncidenciasPorPrioridad(prioridad);
    }

    @PutMapping("/{id}/estado")
    public IncidenciaResponseDTO actualizarEstado(
            @PathVariable Integer id,
            @RequestParam EstadoIncidencia estado
    ) {
        return incidenciaService.actualizarEstado(id, estado);
    }

    @PutMapping("/{id}/prioridad")
    public IncidenciaResponseDTO actualizarPrioridad(
            @PathVariable Integer id,
            @RequestParam PrioridadIncidencia prioridad
    ) {
        return incidenciaService.actualizarPrioridad(id, prioridad);
    }
}
