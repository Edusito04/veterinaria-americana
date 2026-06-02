package com.veterinaria.americana.repository;

import com.veterinaria.americana.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Buscar por email para Spring Security (Autenticación)
    Optional<Usuario> findByEmail(String email);
    
    // Validar si el correo ya existe al registrarse
    boolean existsByEmail(String email);
}