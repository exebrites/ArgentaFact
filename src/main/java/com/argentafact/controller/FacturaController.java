package com.argentafact.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.argentafact.model.EstadoFactura;
import com.argentafact.model.Factura;
import com.argentafact.model.TipoFactura;
import com.argentafact.service.FacturaService;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/facturas")
public class FacturaController {
    @Autowired
    private FacturaService facturaService;

    @GetMapping("/")
    public String listarFacturas(Model model) {
        var facturas = facturaService.obtenerFacturas();
        model.addAttribute("facturas", facturas);
        return "factura/listar";
    }
    @GetMapping("/nuevo")
    public String nuevaFactura(){
        var factura  =new Factura();
        factura.setNumeroFactura("123");
        factura.setFechaEmision(LocalDate.now());
        factura.setTipoFactura(TipoFactura.A);
        factura.setTotal(BigDecimal.ZERO);
        factura.setEstado(EstadoFactura.EMITIDA);
        facturaService.guardarFactura(factura);
        return "redirect:/facturas/";
    }

}
