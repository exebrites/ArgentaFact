package com.argentafact.service;

import org.springframework.stereotype.Service;

import com.argentafact.model.ServicioContratado;
import com.argentafact.repository.ServicioContratadoRepository;

@Service
public class ServicioContratadoService {
    private final ServicioContratadoRepository servicioContratadoRepository;

    public ServicioContratadoService(ServicioContratadoRepository servicioContratadoRepository) {
        this.servicioContratadoRepository = servicioContratadoRepository;
    }

    public void guardar(ServicioContratado servicioContratado) {
        this.servicioContratadoRepository.save(servicioContratado);
    }
}
