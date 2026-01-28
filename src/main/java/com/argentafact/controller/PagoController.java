package com.argentafact.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.argentafact.model.*;
import com.argentafact.repository.EmpleadoRepository;
import com.argentafact.service.FacturaService;
import com.argentafact.service.PagoService;

@Controller
@RequestMapping("/pagos/")
public class PagoController {

    private final PagoService pagoService;
    private final EmpleadoRepository empleadoRepository;
    private final FacturaService facturaService;

    public PagoController(PagoService pagoService,                          
                           EmpleadoRepository empleadoRepository,
                           FacturaService facturaService) {
        this.pagoService = pagoService;
        
        this.empleadoRepository = empleadoRepository;
        this.facturaService = facturaService;
    }

    @GetMapping ({ "", "/" })
    public String listarPagos(Model model) {
        model.addAttribute("pagos", pagoService.listarPagos());
        return "pago/listarPago";
    }
    
    @GetMapping("/nuevo")
public String mostrarFormulario(Model model) {
    
        model.addAttribute("tiposPago", TipoPago.values());

        List<Factura> facturasPendientes = facturaService.obtenerFacturasConSaldoPendiente();

        if (facturasPendientes == null) {
            facturasPendientes = List.of();
        }
        model.addAttribute("facturasPendientes", facturasPendientes);
        return "pago/registrarPago";
    }



    @PostMapping("/registrar")
    public String registrarPago(@RequestParam Long idFactura,
                                @RequestParam BigDecimal monto,
                                @RequestParam TipoPago tipoPago,
                                RedirectAttributes redirectAttributes) {

        Empleado empleado = empleadoRepository.findAll()
            .stream()
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No hay empleados registrados"));

        pagoService.registrarPago(idFactura, monto, tipoPago, empleado);

        redirectAttributes.addFlashAttribute(
                "mensaje", "Pago registrado correctamente");

        return "redirect:/pagos/";
    }
}
