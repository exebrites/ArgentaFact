package com.argentafact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.argentafact.service.ClienteService;
import com.argentafact.service.CuentaService;

@Controller
@RequestMapping("/cuentas/")
public class CuentaController {

    private final CuentaService cuentaService;
    private final ClienteService clienteService;

    public CuentaController(CuentaService cuentaService,
                            ClienteService clienteService) {
        this.cuentaService = cuentaService;
        this.clienteService = clienteService;
    }


    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cuentas", cuentaService.listarTodas());
        return "cuenta/listar";
    }


    @GetMapping("/{id}")
    public String ver(@PathVariable Long id, Model model) {
        model.addAttribute("cuenta", cuentaService.obtenerPorId(id));
        return "cuenta/verCuenta";
    }


    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("clientes", clienteService.buscarTodos());
        return "cuenta/nuevaCuenta";
    }


    @PostMapping("/guardar")
    public String guardar(@RequestParam Long idCliente,
                          RedirectAttributes ra) {
        try {
            cuentaService.crearCuenta(idCliente);
            ra.addFlashAttribute("mensaje", "Cuenta creada correctamente");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cuentas/";
    }
}
