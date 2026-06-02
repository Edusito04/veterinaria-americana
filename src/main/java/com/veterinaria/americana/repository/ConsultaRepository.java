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
    
   
    List<Consulta> findByMascotaIdOrderByFechaHoraDesc(Long mascotaId);
    
  
    List<Consulta> findByFechaHoraBetween(LocalDateTime inicio, LocalDateTime fin);
    
  
    List<Consulta> findByEstadoAndFechaHoraBetween(String estado, LocalDateTime inicio, LocalDateTime fin);
}
