package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.argentafact.model.Cliente;
import com.argentafact.model.CondicionFiscal;
import com.argentafact.service.ClienteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    // @GetMapping("/")
    // public String listarClientes(Model model) {
    // var clientes = clienteService.buscarTodos();
    // model.addAttribute("clientes", clientes);
    // return "cliente/listar";
    // }
    @GetMapping("/")
    public String listarProductos(
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
        model.addAttribute("cliente", cliente);
        model.addAttribute("condicionesFiscales", CondicionFiscal.values());
        return "cliente/nuevoCliente";
    }

    @PostMapping("/")
    public String agregarCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {
        clienteService.guardar(cliente);
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
    public String actualizarCliente(@PathVariable("id") Integer id,
            @ModelAttribute("cliente") Cliente cliente) {
        clienteService.actualizarClientePorId((long) id, cliente);
        return "redirect:/clientes/";
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
