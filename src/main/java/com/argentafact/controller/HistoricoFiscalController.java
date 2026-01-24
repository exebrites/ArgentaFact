package com.argentafact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.argentafact.service.HistoricoFiscalService;

@Controller
@RequestMapping("/historico/")
public class HistoricoFiscalController {

    private final HistoricoFiscalService service;

    public HistoricoFiscalController(HistoricoFiscalService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("historicos", service.listarTodos());
        return "historico/listar";
    }

    @GetMapping("/{id}")
    public String ver(@PathVariable Long id, Model model) {
        model.addAttribute("historico", service.obtenerPorId(id));
        return "historico/verHistorico";
    }
}
