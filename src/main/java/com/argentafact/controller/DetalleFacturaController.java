package com.argentafact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.argentafact.model.DetalleDeFacturaFormulario;
import com.argentafact.model.DetalleFactura;
import com.argentafact.model.Servicio;
import com.argentafact.service.ServicioService;

@Controller
@SessionAttributes("detalle") // <-- ¡Aquí está la clave!
public class DetalleFacturaController {
    @Autowired
    private ServicioService servicioService;

    /**
     * Este método inicializa el objeto 'carrito' la primera vez que se accede
     * y Spring lo guarda en la sesión bajo el nombre "carrito".
     */
    @ModelAttribute("detalle")
    public DetalleDeFacturaFormulario setUpDeFacturaFormulario() {
        return new DetalleDeFacturaFormulario();
    }

    @GetMapping("/detalleProducto")
    public String verDetalleProducto(Model model) {
        // El objeto "carrito" se inyecta automáticamente desde la sesión al modelo.
        // model.addAttribute("carrito", ...); // No hace falta añadirlo explícitamente
        // si usas @SessionAttributes

        var servicios = servicioService.buscarTodos();
        model.addAttribute("servicios", servicios);
        return "factura/vistaDetalleFactura"; // Renderiza una vista HTML/JSP/Thymeleaf
    }

    /**
     * Método para añadir un producto al carrito almacenado en la sesión.
     */
    @GetMapping("/agregarAlCarrito")
    public String agregarProducto(@RequestParam("nombre") Long nombreServicio,
            @ModelAttribute("detalle") DetalleDeFacturaFormulario detalle) {

        // Spring recupera automáticamente el 'carrito' de la sesión,
        // lo modifica con la nueva información.
        var servicio = servicioService.findById(nombreServicio);

        detalle.agregarServicio(servicio.getNombre(), 50.0);

        // Al finalizar el método, Spring persiste los cambios del 'carrito' en la
        // sesión.
        return "redirect:/facturas/crear";
    }

    /**
     * Método para "finalizar" la sesión de compra y limpiar el atributo específico.
     */
    @GetMapping("/finalizarCompra")
    public String finalizarCompra(SessionStatus status) {
        // Procesa el pago, etc.

        // Limpia el atributo "carrito" de la sesión.
        status.setComplete();
        return "compraConfirmada";
    }
}
