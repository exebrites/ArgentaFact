package com.argentafact.service;

import java.util.List;
import java.util.Optional;

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

    public List<ServicioContratado> buscarTodos() {
        return this.servicioContratadoRepository.findAll();
    }


    public ServicioContratado findByIdServicioContratado(Long servicioContratado_id) {
        return this.servicioContratadoRepository.findByIdServicioContratado(servicioContratado_id);
    }
}
