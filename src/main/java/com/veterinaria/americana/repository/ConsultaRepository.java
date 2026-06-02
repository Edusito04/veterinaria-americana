package com.veterinaria.americana.repository;

import com.veterinaria.americana.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    
    // Consulta 1: Historial médico de una mascota ordenado por fecha
    List<Consulta> findByMascotaIdOrderByFechaHoraDesc(Long mascotaId);
    
    // Consulta 2: Agenda/Calendario del veterinario (Citas en un rango de tiempo, ej: hoy)
    List<Consulta> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    
    // Reporte 1: Citas atendidas en un rango de fechas
    List<Consulta> findByEstadoAndFechaHoraBetween(String estado, LocalDateTime inicio, LocalDateTime fin);
}