package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.argentafact.model.Cliente;
import com.argentafact.model.CondicionFiscal;
import com.argentafact.model.Departamento;
import com.argentafact.service.ClienteService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping("/")
    public String listarClientes(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamano,
            Model model) {

        Page<Cliente> paginaClientes = clienteService.buscarTodos(
                PageRequest.of(pagina, tamano));

        // Solo lo esencial al modelo
        model.addAttribute("clientes", paginaClientes.getContent());
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", paginaClientes.getTotalPages());

        return "cliente/listar";
    }

    @GetMapping("/crear")
    public String nuevoCliente(Model model) {
        var cliente = new Cliente();
        model.addAttribute("localidades", Departamento.values());
        model.addAttribute("cliente", cliente);
        model.addAttribute("condicionesFiscales", CondicionFiscal.values());
        return "cliente/nuevoCliente";
    }

    @PostMapping("/")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model) {
        if (result.hasErrors())
            return "cliente/nuevoCliente";

        try {
            clienteService.guardar(cliente);
        } catch (Exception e) {
            // Pasamos el mensaje de error del Service a la vista
            model.addAttribute("errorCuit", e.getMessage());
            return "cliente/nuevoCliente";
        }
        return "redirect:/clientes/";
    }

    @GetMapping("/{id}")
    public String verCliente(@PathVariable("id") Long id, Model model) {
        var cliente = clienteService.buscarPorId(id);
        model.addAttribute("cliente", cliente);
        return "cliente/verCliente";
    }

    @GetMapping("/{id}/editar")
    public String editarCliente(@PathVariable("id") Long id, Model model) {
        var cliente = clienteService.buscarPorId(id);
        model.addAttribute("cliente", cliente);
        model.addAttribute("condicionesFiscales", CondicionFiscal.values());
        return "cliente/actualizarCliente";
    }

    @PutMapping("/{id}")
    public String actualizarCliente(@PathVariable("id") Long id,
            @Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult result,
            Model model) {
        
        if (result.hasErrors()) {
            prepareModelForForm(model); 
            return "clientes/editarCliente";
        }

        try {
            
            clienteService.actualizarClientePorId(id, cliente);
        } catch (Exception e) {
            
            model.addAttribute("errorCuit", e.getMessage());
            prepareModelForForm(model);
            return "clientes/editarCliente";
        }

        return "redirect:/clientes/";
    }

    private void prepareModelForForm(Model model) {
        model.addAttribute("localidades", Departamento.values());
        model.addAttribute("condicionesFiscales", CondicionFiscal.values());
    }

    @GetMapping("/{id}/eliminar")
    public String eliminarCliente(@PathVariable Long id, Model model) {
        var cliente = clienteService.buscarPorId(id);
        model.addAttribute("cliente", cliente);
        return "cliente/eliminarCliente";
    }

    @DeleteMapping("/{id}")
    public String bajaCliente(@PathVariable("id") Long id) {
        clienteService.eliminarClientePorId(id);
        return "redirect:/clientes/";
    }

}
