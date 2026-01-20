package com.argentafact.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.argentafact.model.Alicuota;
import com.argentafact.repository.AlicuotaRepository;

@Service
public class AlicuotaService {

    @Autowired
    private AlicuotaRepository alicuotaRepository;

    public List<Alicuota> buscarTodas() {
        return alicuotaRepository.findAll();
    }

    public void guardar(Alicuota alicuota) {
        alicuotaRepository.save(alicuota);
    }

    public void eliminar(Long id) {
        alicuotaRepository.deleteById(id);
    }
}
