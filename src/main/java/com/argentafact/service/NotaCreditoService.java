package com.argentafact.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.argentafact.model.Cliente;
import com.argentafact.model.NotaCredito;
import com.argentafact.repository.NotaCreditoRepository;

@Service
public class NotaCreditoService {

    private final NotaCreditoRepository notaCreditoRepository;

    public NotaCreditoService(NotaCreditoRepository notaCreditoRepository) {
        this.notaCreditoRepository = notaCreditoRepository;
    }

    public List<NotaCredito> buscarTodas() {
        return notaCreditoRepository.findAll();
    }
    public void guardarNotaCredito(NotaCredito notaCredito) {
        notaCredito.getFactura().setBaja(true);
        notaCreditoRepository.save(notaCredito);
        // dar de baja la factura asociada
        
    }

    public NotaCredito obtenerNotaCredito(Long id) {
        return notaCreditoRepository.findByIdNotaCredito(id);
    }

    public Page<NotaCredito> buscarTodos(PageRequest of) {
        return notaCreditoRepository.findAll(of);
    }

}
