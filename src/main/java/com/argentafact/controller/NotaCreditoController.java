package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.argentafact.model.NotaCredito;
import com.argentafact.service.FacturaService;
import com.argentafact.service.NotaCreditoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

 

@Controller
@RequestMapping("/notaCredito")
public class NotaCreditoController {
    @Autowired
    private NotaCreditoService notaCreditoService;
    @Autowired
    private FacturaService facturaService;

    // TODO definir un hola mundo de prueba de nota de credito
       // TODO definir listarNotaCredito

    @GetMapping("/")
    public String listarNotaCredito(Model model) {
        var listaNotaCredito =  notaCreditoService.buscarTodas();
        model.addAttribute("listaNotaCredito", listaNotaCredito);
        return "notaCredito/listar";
    }
    

 
    // TODO definir el metodo nuevoCliente 
    @GetMapping("/crear")
    public String nuevoNotaCredito(Model model) {
        var notaCredito = new NotaCredito();
        var listaFacturas =  facturaService.obtenerFacturas();
        model.addAttribute("notaCredito", notaCredito);
        model.addAttribute("listaFacturas", listaFacturas);
        return "notaCredito/nuevoNotaCredito";
    }

    // TODO definir el metodo para agregarNotaCredito
    @PostMapping("/")
    public String agregarNotaCredito(@ModelAttribute("notaCredito") NotaCredito notaCredito) {
        //TODO: process POST request
        notaCreditoService.guardarNotaCredito(notaCredito);
        return "redirect:/notaCredito/";
    }
    
}
