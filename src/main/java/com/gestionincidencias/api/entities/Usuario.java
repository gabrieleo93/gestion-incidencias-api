package com.gestionincidencias.api.entities;

import java.time.LocalDateTime;

import com.gestionincidencias.api.enums.EstadoUsuario;
import com.gestionincidencias.api.enums.RolUsuario;
import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idusuario")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 80)
    private String apellido;

    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, length = 20)
    private RolUsuario rol;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoUsuario estado;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();

        if (this.estado == null) {
            this.estado = EstadoUsuario.ACTIVO;
        }

        if (this.rol == null) {
            this.rol = RolUsuario.USER;
        }
    }
}
