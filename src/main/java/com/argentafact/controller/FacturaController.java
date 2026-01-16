package com.argentafact.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.argentafact.model.DetalleDeFacturaFormulario;
import com.argentafact.model.EstadoFactura;
import com.argentafact.model.Factura;
import com.argentafact.model.Linea;
import com.argentafact.service.ClienteService;
import com.argentafact.service.EmpleadoService;
import com.argentafact.service.FacturaService;
import com.argentafact.service.ServicioService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/facturas")
@SessionAttributes("detalle")
public class FacturaController {
    @Autowired
    private FacturaService facturaService;
    @Autowired
    private EmpleadoService empleadoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ServicioService servicioService;

    @ModelAttribute("detalle")
    public DetalleDeFacturaFormulario setUpDetalleFacturaFormulario() {
        return new DetalleDeFacturaFormulario();
    }

    @GetMapping("/")
    public String listarFacturas(Model model) {
        var facturas = facturaService.obtenerFacturas();
        model.addAttribute("facturas", facturas);
        return "factura/listar";
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

    @GetMapping("/crear")
    public String nuevaFactura(
            @ModelAttribute("detalle") DetalleDeFacturaFormulario detalleFactura,
            Model model) {

        Factura factura = new Factura();
        var clientes = clienteService.buscarTodos();
        var servicios = servicioService.buscarTodos();
        model.addAttribute("factura", factura);
        model.addAttribute("clientes", clientes);
        model.addAttribute("servicios", servicios);

        return "factura/nuevaFactura";
    }

    @GetMapping("/detalleFactura")
    public String verDetalleProducto(@ModelAttribute("detalle") DetalleDeFacturaFormulario detalle, Model model) {
        var servicios = servicioService.buscarTodos();
        model.addAttribute("servicios", servicios);
        return "factura/vistaDetalleFactura";
    }

    @GetMapping("/agregarDetalleFactura")
    public String agregarDetalleFactura(@ModelAttribute("idServicio") Long idServicio,
            @ModelAttribute("detalle") DetalleDeFacturaFormulario detalle) {

        var servicio = servicioService.findById(idServicio);

        var linea = new Linea();
        linea.setNombre(servicio.getNombreServicio());
        linea.setPrecio(servicio.getPrecio());
        linea.setDescripcion(servicio.getDescripcion());
        linea.setIdServicio(servicio.getIdServicio());

        detalle.agregarServicio(linea);

        return "redirect:/facturas/crear";
    }

}
