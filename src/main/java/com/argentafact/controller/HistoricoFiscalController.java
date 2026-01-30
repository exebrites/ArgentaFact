package com.argentafact.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.argentafact.model.HistoricoFiscal;
import com.argentafact.service.HistoricoFiscalService;

@Controller
@RequestMapping("/historico/")
public class HistoricoFiscalController {

    private final HistoricoFiscalService service;

    public HistoricoFiscalController(HistoricoFiscalService service) {
        this.service = service;
    }

    @GetMapping ({ "", "/" })
    public String listar(Model model) {
        model.addAttribute("historicos", service.listarTodos());
        return "historico/listar";
    }

    @GetMapping("/{id}")
    public String ver(@PathVariable Long id, Model model) {
        HistoricoFiscal historico = service.obtenerPorId(id);
        String fechaFormateada = historico.getFechaOperacion()
            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        model.addAttribute("historico", historico);
        model.addAttribute("fechaOperacionFormateada", fechaFormateada);
        return "historico/verHistorico";
    }
}
