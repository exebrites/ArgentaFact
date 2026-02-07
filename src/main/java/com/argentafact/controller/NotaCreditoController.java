package com.argentafact.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        notaCredito.setFechaEmision(LocalDate.now());
        var listaFacturas = facturaService.obtenerFacturasActivas();
        var motivos = Motivo.values();
        model.addAttribute("notaCredito", notaCredito);
        model.addAttribute("listaFacturas", listaFacturas);
        model.addAttribute("motivos", motivos);
        return "notaCredito/nuevoNotaCredito";
    }

    @PostMapping("/")
    public String agregarNotaCredito(@ModelAttribute("notaCredito") NotaCredito notaCredito,
            RedirectAttributes redirectAttributes) {

        try {
            notaCreditoService.guardarNotaCredito(notaCredito);
        } catch (RuntimeException e) {
            if (e.getMessage().equals("La factura no se puede afectar o ya está pagada completamente")) {

                redirectAttributes.addFlashAttribute("error",
                        "La factura no se puede afectar o ya esta pagada completamente");
                return "redirect:/notaCredito/crear";
            }
            if (e.getMessage().equals("El monto de la nota de crédito no puede superar el saldo pendiente")) {
                redirectAttributes.addFlashAttribute("error",
                        "El monto de la nota de crédito no puede superar el saldo pendiente");
                return "redirect:/notaCredito/crear";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/notaCredito/crear";
        }
        return "redirect:/notaCredito/";
    }

}
