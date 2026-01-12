package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.argentafact.model.Cliente;
import com.argentafact.service.ClienteService;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
    public String listarClientes(Model model) {
        var clientes = clienteService.buscarTodos();
        model.addAttribute("clientes", clientes);
        return "cliente/listar";
    }

    @GetMapping("/nuevo")
    public String nuevoCliente() {
        Cliente cliente = new Cliente("Exequiel");
        clienteService.guardar(cliente);
        return "redirect:/clientes/";
    }

}
