package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.argentafact.model.Cliente;
import com.argentafact.model.CondicionFiscal;
import com.argentafact.service.ClienteService;
import org.springframework.web.bind.annotation.PostMapping;
 


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

    @GetMapping("/crear")
    public String nuevoCliente(Model model) {
        var cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        model.addAttribute("condicionesFiscales", CondicionFiscal.values());
        return "cliente/nuevoCliente";
    }
    @PostMapping("/")
    public String agregarCliente(@ModelAttribute ("cliente") Cliente cliente, Model model) {
        clienteService.guardar(cliente);
        return "redirect:/clientes/";
    }
    

}
