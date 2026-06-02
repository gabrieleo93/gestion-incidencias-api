package com.gestionincidencias.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestionincidencias.api.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
Optional<Usuario> findByEmail(String email);
boolean existsByEmail(String email);

}
