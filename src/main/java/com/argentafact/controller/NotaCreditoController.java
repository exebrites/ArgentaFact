package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.argentafact.model.Cliente;
import com.argentafact.model.NotaCredito;
import com.argentafact.service.FacturaService;
import com.argentafact.service.NotaCreditoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/notaCredito")
public class NotaCreditoController {
    @Autowired
    private NotaCreditoService notaCreditoService;
    @Autowired
    private FacturaService facturaService;

    @GetMapping("/")
    public String listarNotaCredito(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamano,
            Model model) {

        Page<NotaCredito> paginaNotaCredito = notaCreditoService.buscarTodos(
                PageRequest.of(pagina, tamano));

        // Solo lo esencial al modelo
        model.addAttribute("listaNotaCredito", paginaNotaCredito.getContent());
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", paginaNotaCredito.getTotalPages());

        return "notaCredito/listarNotaCredito";
    }

    @GetMapping("/crear")
    public String nuevoNotaCredito(Model model) {
        var notaCredito = new NotaCredito();
        var listaFacturas = facturaService.obtenerFacturasActivas();
        var motivos = Motivo.values();
        model.addAttribute("notaCredito", notaCredito);
        model.addAttribute("listaFacturas", listaFacturas);
        model.addAttribute("motivos", motivos);
        return "notaCredito/nuevoNotaCredito";
    }

    @PostMapping("/")
    public String agregarNotaCredito(@ModelAttribute("notaCredito") NotaCredito notaCredito) {
        // TODO: dar de baja la factura
        notaCreditoService.guardarNotaCredito(notaCredito);
        return "redirect:/notaCredito/";
    }

}
