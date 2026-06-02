package com.veterinaria.americana.controller;

import com.veterinaria.americana.entity.Consulta;
import com.veterinaria.americana.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping("/dashboard")
    public String dashboardAdmin(Model model) {
        List<Consulta> citasDelDia = consultaService.obtenerCitasDelDia();
        model.addAttribute("citas", citasDelDia);
        return "admin/dashboard";
    }

    @GetMapping("/consulta/atender/{id}")
    public String mostrarFormAtencion(@PathVariable("id") Long id, Model model) {
        model.addAttribute("consultaId", id);
        return "admin/atender-consulta";
    }

    // CORRECCIÓN AQUÍ: Cambiamos "historialVacunacion" por "vacunas" para enlazar directo con tu Service
    @PostMapping("/consulta/atender/guardar")
    public String registrarAtencion(
            @RequestParam("consultaId") Long consultaId,
            @RequestParam("diagnostico") String diagnostico,
            @RequestParam("tratamiento") String tratamiento,
            @RequestParam("vacunas") String vacunas) { 
        
        consultaService.registrarAtencion(consultaId, diagnostico, tratamiento, vacunas);
        return "redirect:/admin/dashboard?atendidoExito";
    }
}