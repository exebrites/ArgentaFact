package com.argentafact.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findByIdCliente(id);
    }
   

    public void actualizarClientePorId(Long id, Cliente cliente) {
         clienteRepository.findById(id).
            ifPresent(clienteObtenida -> {
                clienteObtenida.setNombre(cliente.getNombre());
                clienteObtenida.setApellido(cliente.getApellido());
                clienteObtenida.setCuit(cliente.getCuit());
                clienteObtenida.setDireccion(cliente.getDireccion());
                clienteObtenida.setTelefono(cliente.getTelefono());
                clienteObtenida.setLocalidad(cliente.getLocalidad());
                clienteObtenida.setCondicionFiscal(cliente.getCondicionFiscal());
                clienteRepository.save(clienteObtenida);
            });
    }
    public void eliminarClientePorId(Long id ){
        clienteRepository.deleteById(id);
    }

    public Page<Cliente> buscarTodos(PageRequest of) {
        return clienteRepository.findAll(of);
    }
}
