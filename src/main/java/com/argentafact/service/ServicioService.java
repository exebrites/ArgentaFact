package com.argentafact.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.argentafact.model.Servicio;
import com.argentafact.repository.ServicioRepository;

@Service
public class ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    public List<Servicio> buscarTodos() {
        return servicioRepository.findAll();
    }

    public void guardar(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    public void eliminar(Long id) {
        servicioRepository.deleteById(id);
    }
}
