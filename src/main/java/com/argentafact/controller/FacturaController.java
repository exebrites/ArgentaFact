package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.argentafact.model.DetalleDeFacturaFormulario;
import com.argentafact.model.DetalleFactura;
import com.argentafact.model.EstadoFactura;
import com.argentafact.model.Factura;
import com.argentafact.model.FacturaSesion;
import com.argentafact.model.Linea;
import com.argentafact.service.ClienteService;
import com.argentafact.service.EmpleadoService;
import com.argentafact.service.FacturaService;
import com.argentafact.service.ServicioService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/facturas")
@SessionAttributes({ "detalle", "facturaSesion" })
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

    @ModelAttribute("facturaSesion")
    public FacturaSesion setUpDeFacturaSesion() {
        return new FacturaSesion();
    }

    @GetMapping("/")
    public String listarFacturas(Model model) {
        var facturas = facturaService.obtenerFacturas();
        model.addAttribute("facturas", facturas);
        return "factura/listar";
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

    @PostMapping("/")
    public String agregarFactura(@ModelAttribute("detalle") DetalleDeFacturaFormulario detalleFactura,
            @ModelAttribute("factura") Factura factura, Model model,
            @ModelAttribute("facturaSesion") FacturaSesion facturaSesion) {
        // TODO : obtener empleado autenticado
        factura.setEmpleado(empleadoService.buscarTodos().get(0));
        factura.setNumeroFactura("123");
        factura.setTotal(detalleFactura.getTotal());
        factura.setEstado(EstadoFactura.PENDIENTE);

        DetalleFactura detalle = new DetalleFactura();
        for (var linea : detalleFactura.getServiciosSeleccionados()) {
            var servicio = servicioService.findById(linea.getIdServicio());
            detalle.setServicio(servicio);
            detalle.setFactura(factura);
            detalle.setSubtotal(linea.getPrecio());
            factura.AgregarDetalle(detalle);
        }

        // factura sesion representa los mismos campos que factura con la funcion de ser
        // rederizable desde otra vista 
        facturaSesion.setNumeroFactura(factura.getNumeroFactura());
        facturaSesion.setFechaEmision(factura.getFechaEmision());
        facturaSesion.setTipoFactura(factura.getTipoFactura());
        facturaSesion.setTotal(factura.getTotal());
        facturaSesion.setEstado(factura.getEstado());
        facturaSesion.setCliente(factura.getCliente());
        facturaSesion.setEmpleado(factura.getEmpleado());
        facturaSesion.setDetalleFacturas(factura.getDetalleFacturas());

        model.addAttribute("factura", factura);
        return "factura/previewFactura";
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

        if (!detalle.estaSeleccionado(linea.getIdServicio())) {

            detalle.agregarServicio(linea);

        }

        return "redirect:/facturas/crear";
    }

    @GetMapping("/confirmarDatos")
    public String confirmarDatos(@ModelAttribute("facturaSesion") FacturaSesion facturaSesion) {
        
        Factura factura = new Factura();
        factura.setNumeroFactura(facturaSesion.getNumeroFactura());
        factura.setFechaEmision(facturaSesion.getFechaEmision());
        factura.setTipoFactura(facturaSesion.getTipoFactura());
        factura.setTotal(facturaSesion.getTotal());
        factura.setEstado(facturaSesion.getEstado());
        factura.setCliente(facturaSesion.getCliente());
        factura.setEmpleado(facturaSesion.getEmpleado());
        factura.setDetalleFacturas(facturaSesion.getDetalleFacturas());
        // relacionar detalle con factura
        for (DetalleFactura detalle : facturaSesion.getDetalleFacturas()) {
            detalle.setFactura(factura);
        }
        facturaService.guardarFactura(factura);

        return "redirect:/facturas/";
    }

    @GetMapping("/limpiarDetalle")
    public String eliminarLinea(
            @ModelAttribute("detalle") DetalleDeFacturaFormulario detalleFactura,
            HttpSession session) {

        detalleFactura.limpiar();

        return "redirect:/facturas/crear";
    }
    // TODO : eliminar un servicio de detalle de factura


    // TODO generar un archivo pdf de la factura 
}
