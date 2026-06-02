package com.veterinaria.americana.repository;

import com.veterinaria.americana.entity.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    // Obtener las mascotas de un cliente específico
    List<Mascota> findByDuenoId(Long usuarioId);
}