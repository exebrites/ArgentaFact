package com.argentafact.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.argentafact.model.EstadoFactura;
import com.argentafact.model.Factura;
import com.argentafact.model.Pago;
import com.argentafact.repository.PagoRepository;
import com.argentafact.service.FacturaService;
import com.argentafact.service.PagoService;

@Controller
public class AuthController {

    @Autowired
    private FacturaService facturaService;
    @Autowired
    private PagoService pagoService;
    @Autowired
    private PagoRepository pagoRepository;

    /**
     * Página de login
     */
    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        // Si el usuario ya está autenticado, redirigir al home
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() &&
                !auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/home";
        }

        // Mensajes de error o éxito
        if (error != null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
        }

        if (logout != null) {
            model.addAttribute("message", "Sesión cerrada correctamente");
        }

        return "login";
    }

    /**
     * Página principal después del login
     */
    @GetMapping("/home")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        // obtener el numero de facturas del mes
        var facturas = facturaService.obtenerFacturasDelMesActual();
        model.addAttribute("facturasDelMes", facturas.size());
        // obtener el numero total de pagos del mes
        var totalPagosMes = pagoService.obtenerTotalPagosDelMesActual();
        model.addAttribute("totalPagosMes", totalPagosMes);
        //   obtener el numero total de facturas impagas
       var totalFacturasImpagas = facturaService.obtenerTotalFacturasImpagas();
        model.addAttribute("totalFacturasImpagas", totalFacturasImpagas);
        return "dashboard";
    }

    /**
     * Ruta raíz redirige al home
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }

}
