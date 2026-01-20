package com.argentafact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.argentafact.model.NotaCredito;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

 

@Controller
@RequestMapping("/notaCredito")
public class NotaCreditoController {
    // TODO definir un hola mundo de prueba de nota de credito
       // TODO definir listarNotaCredito

    @GetMapping("/")
    public String listarNotaCredito() {
        return "notaCredito/listar";
    }
    

 
    // TODO definir el metodo nuevoCliente 
    @GetMapping("/crear")
    public String nuevoNotaCredito(Model model) {
        var notaCredito = new NotaCredito();
        model.addAttribute("notaCredito", notaCredito);
        return "notaCredito/nuevoNotaCredito";
    }

    // TODO definir el metodo para agregarNotaCredito
    @PostMapping("/")
    public String agregarNotaCredito(@ModelAttribute("notaCredito") NotaCredito notaCredito) {
        //TODO: process POST request
        
        return "redirect:/notaCredito/";
    }
    
}
