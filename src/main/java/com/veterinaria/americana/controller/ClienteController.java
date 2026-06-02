package com.veterinaria.americana.controller;

import com.veterinaria.americana.entity.Consulta;
import com.veterinaria.americana.entity.Mascota;
import com.veterinaria.americana.entity.Usuario;
import com.veterinaria.americana.service.ConsultaService;
import com.veterinaria.americana.service.MascotaService;
import com.veterinaria.americana.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private MascotaService mascotaService;

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private UsuarioService usuarioService;

    // 1. Panel de usuario principal
    @GetMapping("/panel")
    public String panelCliente(Principal principal, Model model) {
        Usuario cliente = usuarioService.buscarPorEmail(principal.getName());
        List<Mascota> misMascotas = mascotaService.listarPorDueno(cliente.getId());
        
        model.addAttribute("usuario", cliente);
        model.addAttribute("mascotas", misMascotas);
        return "cliente/panel"; 
    }

    // 2. Registrar Mascota (Formulario)
    @GetMapping("/mascota/nuevo")
    public String mostrarFormMascota(Model model) {
        model.addAttribute("mascota", new Mascota());
        return "cliente/registrar-mascota";
    }

    // 3. Guardar Mascota (Mantenimiento CRUD)
    @PostMapping("/mascota/guardar")
    public String guardarMascota(@ModelAttribute("mascota") Mascota mascota, Principal principal) {
        Usuario cliente = usuarioService.buscarPorEmail(principal.getName());
        mascota.setDueno(cliente); 
        mascotaService.guardar(mascota);
        return "redirect:/cliente/panel";
    }

    // 4. Caso de Uso Transaccional: Formulario para agendar consulta
    @GetMapping("/agenda/nuevo")
    public String mostrarFormCita(Principal principal, Model model) {
        Usuario cliente = usuarioService.buscarPorEmail(principal.getName());
        List<Mascota> misMascotas = mascotaService.listarPorDueno(cliente.getId());
        
        model.addAttribute("consulta", new Consulta());
        model.addAttribute("mascotas", misMascotas);
        return "cliente/agendar-consulta";
    }

    // 5. Guardar Cita de manera transaccional (Ruta corregida para el formulario)
    @PostMapping("/agenda/guardar")
    public String agendarCita(@ModelAttribute("consulta") Consulta consulta) {
        // Forzamos que la consulta inicialice como PENDIENTE de forma segura antes de guardar
        consulta.setEstado("PENDIENTE");
        consultaService.agendarCita(consulta);
        return "redirect:/cliente/panel?agendaExito";
    }

    // 6. Consultar historial clínico y de vacunación de una mascota
    @GetMapping("/mascota/historial/{id}")
    public String verHistorialMascota(@PathVariable("id") Long id, Model model) {
        Mascota mascota = mascotaService.buscarPorId(id);
        List<Consulta> historial = consultaService.obtenerHistorialMascota(id); 
        
        model.addAttribute("mascota", mascota);
        model.addAttribute("historial", historial);
        return "cliente/historial";
    }
}