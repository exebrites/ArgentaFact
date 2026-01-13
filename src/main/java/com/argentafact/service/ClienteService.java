package com.argentafact.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.argentafact.model.Cliente;
import com.argentafact.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    public void guardar(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public Cliente buscarPorId(Integer id) {
        return clienteRepository.findByIdCliente(id);}
}
