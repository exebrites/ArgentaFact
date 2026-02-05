package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.argentafact.model.DetalleServicioContratado;
import com.argentafact.model.LineaServicioContratado;
import com.argentafact.model.ServicioContratado;
import com.argentafact.service.ClienteService;
import com.argentafact.service.ServicioContratadoService;
import com.argentafact.service.ServicioService;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/servicioContratado")
@SessionAttributes({ "detalle" })
public class ServicioContratadoController {
    @Autowired
    private ServicioService servicioService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ServicioContratadoService servicioContratadoService;

    @ModelAttribute("detalle")
    public DetalleServicioContratado setUpDetalleServicioContratado() {
        return new DetalleServicioContratado();
    }

    @GetMapping("/")
    public String listarServiciosContratados(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamano,
            Model model) {

        Page<ServicioContratado> paginaClientes = servicioContratadoService.buscarTodos(PageRequest.of(pagina, tamano));
      
        model.addAttribute("serviciosContratados", paginaClientes.getContent());
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("totalPaginas", paginaClientes.getTotalPages());
         
        return "servicioContratado/listar";
    }

    @GetMapping("/crear")
    public String nuevoServiciosContratados(Model model, @ModelAttribute("detalle") DetalleServicioContratado detalle) {
        var clientes = clienteService.buscarTodos();
        var servicioContratado = new ServicioContratado();
        model.addAttribute("clientes", clientes);
        model.addAttribute("servicioContratado", servicioContratado);
        return "servicioContratado/nuevoServicioContratado";
    }

    @PostMapping("/")
    public String agregarFactura(@ModelAttribute("detalle") DetalleServicioContratado detalle,
            @ModelAttribute("servicioContratado") ServicioContratado servicioContratado, Model model,
            RedirectAttributes redirectAttributes, SessionStatus status) {

        // control de detalle vacio
        if (detalle.getServiciosSeleccionados().isEmpty()) {
            redirectAttributes.addFlashAttribute("mensajeError", "Debe agregar un servicio a  la factura");
            return "redirect:/servicioContratado/crear";
        }
        var cliente = servicioContratado.getCliente();
        // crear el contrato por cada servicio y su cliente
        for (LineaServicioContratado linea : detalle.getServiciosSeleccionados()) {

            var servicio = servicioService.findById(linea.getIdServicio());
            var nuevoServicioContratado = new ServicioContratado(cliente, servicio);
            servicioContratadoService.guardar(nuevoServicioContratado);
        }
        status.setComplete();

        return "redirect:/servicioContratado/";
    }

    @GetMapping("/detalleServicioContratado")
    // public String verDetalleProducto(@ModelAttribute("detalle")
    // DetalleDeFacturaFormulario detalle, Model model) {

    public String verServicioPorContratar(Model model) {
        var servicios = servicioService.buscarTodos();
        model.addAttribute("servicios", servicios);
        return "servicioContratado/agregarServicioContratado";
    }

    @GetMapping("/agregarDetalleServicioContratado")
    public String agregarDetalleServicioContratado(@ModelAttribute("idServicio") Long idServicio,
            @ModelAttribute("detalle") DetalleServicioContratado detalle, RedirectAttributes redirectAttributes) {

        var servicio = servicioService.findById(idServicio);
        var linea = new LineaServicioContratado();
        linea.setNombre(servicio.getNombreServicio());
        linea.setPrecio(servicio.getPrecio());
        linea.setDescripcion(servicio.getDescripcion());
        linea.setIdServicio(servicio.getIdServicio());

        for (LineaServicioContratado detalleLinea : detalle.getServiciosSeleccionados()) {
            if (detalleLinea.getIdServicio() == idServicio) {
                redirectAttributes.addFlashAttribute("mensajeError", "Este servicio ya fue agregado");
                return "redirect:/servicioContratado/crear";
            }
        }
        detalle.agregarServicio(linea);

        return "redirect:/servicioContratado/crear";
    }

    // @GetMapping("/limpiarDetalle")
    // public String eliminarLinea(
    // @ModelAttribute("detalle") DetalleDeFacturaFormulario detalleFactura,
    // HttpSession session) {

    // detalleFactura.limpiar();

    // return "redirect:/facturas/crear";
    // }
    // // eliminar un servicio de detalle de factura

    // @GetMapping("/eliminarDetalle/{idServicio}")
    // public String eliminarDetalleFactura(@PathVariable Long idServicio,
    // @ModelAttribute("detalle") DetalleDeFacturaFormulario detalle,
    // RedirectAttributes redirectAttributes) {

    // detalle.eliminarServicio(idServicio);

    // return "redirect:/facturas/crear";
    // }


    @GetMapping("/{id}/verServicioContratado")
    public String verServicioContratado(@PathVariable Long id, Model model) {
        var servicioContratado = servicioContratadoService.findById(id);
        model.addAttribute("servicioContratado", servicioContratado);
        return "servicioContratado/verServicioContratado";
    }
    
}
