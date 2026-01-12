package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.argentafact.model.Cliente;
import com.argentafact.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/")
    public String listarClientes() {
        return "cliente/listar";
    }

    @GetMapping("/nuevo")
    public String nuevoCliente() {
        Cliente cliente = new Cliente("Exequiel");
        clienteRepository.save(cliente);
        return "redirect:/clientes/";
    }

}
