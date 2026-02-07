package com.argentafact.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.argentafact.model.Servicio;
import com.argentafact.repository.ServicioRepository;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public void guardar(Servicio servicio) {
        servicioRepository.save(servicio);
    }

    public void eliminar(Long id) {
        servicioRepository.deleteById(id);
    }

    public List<Servicio> buscarTodos() {
        return servicioRepository.findAll();
    }

    public Page<Servicio> buscarTodos(PageRequest of) {
    
    
        return servicioRepository.findAll(of);
    }

    public Servicio findById(Long id) {
        return servicioRepository.findByIdServicio(id);
    }

    public void actualizarServicioPorId(Long id, Servicio servicio) {

        servicioRepository.findById(id).ifPresent(servicioObtenida -> {
            servicioObtenida.setNombreServicio(servicio.getNombreServicio());
            servicioObtenida.setDescripcion(servicio.getDescripcion());
            servicioObtenida.setPrecio((servicio.getPrecio()));
            servicioRepository.save(servicioObtenida);
        });
    }

}
