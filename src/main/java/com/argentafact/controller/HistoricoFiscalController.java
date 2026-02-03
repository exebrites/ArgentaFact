package com.argentafact.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import com.argentafact.model.HistoricoFiscal;
import com.argentafact.service.HistoricoFiscalService;

@Controller
@RequestMapping("/historico/")
public class HistoricoFiscalController {

    private final HistoricoFiscalService service;

    public HistoricoFiscalController(HistoricoFiscalService service) {
        this.service = service;
    }

    @GetMapping({ "", "/" })
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<HistoricoFiscal> historicosPage = service.buscarTodos(PageRequest.of(page, size));

        model.addAttribute("historicos", historicosPage.getContent());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", historicosPage.getTotalPages());

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
