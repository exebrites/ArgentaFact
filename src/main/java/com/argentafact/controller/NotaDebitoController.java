package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.argentafact.model.NotaDebito;
import com.argentafact.service.FacturaService;
import com.argentafact.service.NotaDebitoService;

@Controller
@RequestMapping("/notaDebito")
public class NotaDebitoController {

    @Autowired
    private NotaDebitoService notaDebitoService;

    @Autowired
    private FacturaService facturaService;

    @GetMapping("/")
    public String listarNotaDebito(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamano,
            Model model) {

        Page<NotaDebito> paginaNotaDebito = notaDebitoService.buscarTodos(
                PageRequest.of(pagina, tamano));

        model.addAttribute("listaNotaDebito", paginaNotaDebito.getContent());
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", paginaNotaDebito.getTotalPages());

        return "notaDebito/listarNotaDebito";
    }

    @GetMapping("/crear")
    public String nuevoNotaDebito(Model model) {
        var notaDebito = new NotaDebito();
        var listaFacturas = facturaService.obtenerFacturasActivas();
        var motivos = Motivo.values();
        model.addAttribute("notaDebito", notaDebito);
        model.addAttribute("listaFacturas", listaFacturas);
        model.addAttribute("motivos", motivos);
        return "notaDebito/nuevoNotaDebito";
    }

    @PostMapping("/")
    public String agregarNotaDebito(@ModelAttribute("notaDebito") NotaDebito notaDebito) {
        notaDebitoService.guardarNotaDebito(notaDebito);
        return "redirect:/notaDebito/";
    }
}
