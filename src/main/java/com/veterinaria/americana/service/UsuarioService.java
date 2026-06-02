package com.veterinaria.americana.service;

import com.veterinaria.americana.entity.Usuario;
import com.veterinaria.americana.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public Usuario registrarCliente(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("El correo ya está registrado.");
        }
        
        // 1. Encriptar contraseña de forma nativa por Java (aquí se guardará con los 60 caracteres completos)
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        
        // 2. Controlar la asignación de roles de forma inteligente desde el código
        if ("admin@veterinaria.com".equalsIgnoreCase(usuario.getEmail())) {
            usuario.setRol("ROLE_ADMIN");  // Si se registra con este correo, Java le da poder de Admin
        } else {
            usuario.setRol("ROLE_CLIENTE"); // Cualquier otro correo sigue siendo Cliente normal
        }
        
        return usuarioRepository.save(usuario);
    }
    
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }
}