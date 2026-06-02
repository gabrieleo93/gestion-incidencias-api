package com.gestionincidencias.api.entities;

import com.gestionincidencias.api.enums.EstadoIncidencia;
import com.gestionincidencias.api.enums.PrioridadIncidencia;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idincidencia")
    private Integer id;

    @Column(name = "titulo", nullable = false, length = 120)
    private String titulo;

    @Column(name = "descripcion", nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoIncidencia estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", nullable = false, length = 30)
    private PrioridadIncidencia prioridad;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "fecha_cierre")
    private LocalDateTime fechaCierre;

    @ManyToOne
    @JoinColumn(name = "usuario_creador_id", nullable = false)
    private Usuario usuarioCreador;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();

        if (this.estado == null) {
            this.estado = EstadoIncidencia.ABIERTA;
        }

        if (this.prioridad == null) {
            this.prioridad = PrioridadIncidencia.MEDIA;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();

        if (this.estado == EstadoIncidencia.CERRADA && this.fechaCierre == null) {
            this.fechaCierre = LocalDateTime.now();
        }
    }
}