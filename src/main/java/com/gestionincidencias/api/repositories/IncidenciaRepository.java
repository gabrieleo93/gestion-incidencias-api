package com.gestionincidencias.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestionincidencias.api.entities.Incidencia;
import com.gestionincidencias.api.enums.EstadoIncidencia;
import com.gestionincidencias.api.enums.PrioridadIncidencia;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Integer> {
 List<Incidencia> findByUsuarioCreador_Id(Integer usuarioId);
 List<Incidencia> findByEstado(EstadoIncidencia estado);
 List<Incidencia> findByPrioridad(PrioridadIncidencia prioridad);
}
