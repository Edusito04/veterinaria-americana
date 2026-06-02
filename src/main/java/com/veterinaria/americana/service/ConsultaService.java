package com.veterinaria.americana.service;

import com.veterinaria.americana.entity.Consulta;
import com.veterinaria.americana.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository; // Conexión obligatoria con el repositorio

    // CASO DE USO TRANSACCIONAL: Agendar una cita médica
    @Transactional
    public Consulta agendarCita(Consulta consulta) {
        consulta.setEstado("PENDIENTE");
        return consultaRepository.save(consulta);
    }

    // CASO DE USO TRANSACCIONAL: Registrar datos de atención y tratamiento (Veterinario)
    @Transactional
    public Consulta registrarAtencion(Long consultaId, String diagnostico, String tratamiento, String vacunas) {
        Consulta consulta = consultaRepository.findById(consultaId)
                .orElseThrow(() -> new RuntimeException("Consulta no encontrada"));
        
        consulta.setDiagnostico(diagnostico);
        consulta.setTratamiento(tratamiento);
        consulta.setHistorialVacunacion(vacunas);
        consulta.setEstado("ATENDIDA"); // Cambia el estado de la transacción de forma segura
        
        return consultaRepository.save(consulta);
    }

    // CONSULTA 1: Historial por mascota
    public List<Consulta> obtenerHistorialMascota(Long mascotaId) {
        return consultaRepository.findByMascotaIdOrderByFechaHoraDesc(mascotaId);
    }

    // CONSULTA 2: Ver calendario/citas del día de hoy
 // Reemplaza SOLO este método en tu ConsultaService
    public List<Consulta> obtenerCitasDelDia() {
        // En lugar de usar .atTime(23, 59, 59), calculamos el rango sumando un día completo de forma nativa
        LocalDateTime inicio = LocalDate.now().atStartOfDay(); // 00:00:00 de hoy
        LocalDateTime fin = inicio.plusDays(1);                // 00:00:00 de mañana
        
        // Esto evita errores de conversión de milisegundos en la base de datos
        return consultaRepository.findByFechaHoraBetween(inicio, fin);
    }

    // REPORTE: Citas atendidas en rango de fechas
    public List<Consulta> generarReporteCitasAtendidas(LocalDateTime inicio, LocalDateTime fin) {
        return consultaRepository.findByEstadoAndFechaHoraBetween("ATENDIDA", inicio, fin);
    }
}