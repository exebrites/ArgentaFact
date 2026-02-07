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
@SessionAttributes("detalle") 
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
