package com.argentafact.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/facturas")
public class FacturaController {
    
    @GetMapping("/")
    public String listarFacturas() {
        return "factura/listar";
    }
    
}
