package com.argentafact.controller;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.argentafact.model.DetalleDeFacturaFormulario;
import com.argentafact.model.EstadoFactura;
import com.argentafact.model.Factura;
import com.argentafact.service.ClienteService;
import com.argentafact.service.EmpleadoService;
import com.argentafact.service.FacturaService;
import com.argentafact.service.ServicioService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/facturas")
@SessionAttributes("detalle") // <-- ¡Importante incluirlo aquí también!
public class FacturaController {
    @Autowired
    private FacturaService facturaService;
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ServicioService servicioService;

    @GetMapping("/")
    public String listarFacturas(Model model) {
        var facturas = facturaService.obtenerFacturas();
        model.addAttribute("facturas", facturas);
        return "factura/listar";
    }

    @GetMapping("/crear")
    public String nuevaFactura( Model model) {
        Factura factura = new Factura();
        var clientes = clienteService.buscarTodos();
        var servicios = servicioService.buscarTodos();
        model.addAttribute("factura", factura);
        model.addAttribute("clientes", clientes);
        model.addAttribute("servicios", servicios);
       

        return "factura/nuevaFactura";
    }

    @PostMapping("/")
    public String agregarFactura(@ModelAttribute("factura") Factura factura) {
        // TODO : obtener empleado autenticado
        factura.setEmpleado(empleadoService.buscarTodos().get(0));
        factura.setNumeroFactura("123");
        factura.setTotal(new BigDecimal(100));
        factura.setEstado(EstadoFactura.PENDIENTE);
        facturaService.guardarFactura(factura);
        return "redirect:/facturas/";
    }

}
