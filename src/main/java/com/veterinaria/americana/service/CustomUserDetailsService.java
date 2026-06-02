package com.veterinaria.americana.service;

import com.veterinaria.americana.entity.Usuario;
import com.veterinaria.americana.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscamos el usuario en la base de datos por el correo ingresado en el formulario
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));

        // Retornamos el objeto User de Spring Security con sus roles correspondientes
        return User.builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword()) 
                .roles(usuario.getRol().replace("ROLE_", "")) // Quita el prefijo si ya lo tiene para evitar duplicados
                .build();
    }
}