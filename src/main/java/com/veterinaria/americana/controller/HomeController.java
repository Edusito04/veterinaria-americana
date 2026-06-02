package com.veterinaria.americana.controller;

import com.veterinaria.americana.entity.Usuario;
import com.veterinaria.americana.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @Autowired
    private UsuarioService usuarioService;

    // Pagina Home vista pública
    @GetMapping("/")
    public String home() {
        return "home"; // Busca templates/home.html
    }

    // Pagina registro (Mostrar Formulario)
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro"; // Busca templates/registro.html
    }

    // Procesar el registro del cliente
    @PostMapping("/registro")
    public String registrarCliente(@ModelAttribute("usuario") Usuario usuario, Model model) {
        try {
            usuarioService.registrarCliente(usuario);
            return "redirect:/login?registrado";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }
}