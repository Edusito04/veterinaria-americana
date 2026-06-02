package com.veterinaria.americana.service;

import com.veterinaria.americana.entity.Mascota;
import com.veterinaria.americana.repository.MascotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    public List<Mascota> listarPorDueno(Long usuarioId) {
        return mascotaRepository.findByDuenoId(usuarioId);
    }

    @Transactional
    public Mascota guardar(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public Mascota buscarPorId(Long id) {
        return mascotaRepository.findById(id).orElse(null);
    }

    @Transactional
    public void eliminar(Long id) {
        mascotaRepository.deleteById(id);
    }
}