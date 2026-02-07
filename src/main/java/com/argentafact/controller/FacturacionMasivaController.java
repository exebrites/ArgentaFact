package com.argentafact.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.argentafact.model.Empleado;
import com.argentafact.model.EstadoFactura;
import com.argentafact.model.EstadoServicioContratado;
import com.argentafact.model.Factura;
import com.argentafact.model.ServicioContratado;
import com.argentafact.model.TipoFactura;
import com.argentafact.model.Usuario;
import com.argentafact.repository.UsuarioRepository;
import com.argentafact.service.ClienteService;
import com.argentafact.service.EmpleadoService;
import com.argentafact.service.FacturaService;
import com.argentafact.service.ServicioContratadoService;
import com.argentafact.service.ServicioService;

@Controller
@RequestMapping("/facturacion-masiva")
public class FacturacionMasivaController {
        @Autowired
        private ServicioContratadoService servicioContratadoService;
        @Autowired
        private FacturaService facturaService;
        @Autowired
        private ClienteService clienteService;
        @Autowired
        private ServicioService servicioService;
        @Autowired
        private EmpleadoService empleadoService;
        @Autowired
        private UsuarioRepository usuarioRepository;

        @GetMapping("/")
        public String facturarMasivo(Model model, RedirectAttributes redirectAttributes) {
                // obtener todos los contratos activos del mes actual

                var serviciosActuales = servicioContratadoService.obtenerServiciosContratadosActivosDelMesActual();

                // verificar si tienen que no tengan facturas en el mes actual
                // todas las facturas de este mes
                var facturasDelMes = facturaService
                                .obtenerFacturasDelMesActual();

                // obtener servicios a facturar que no tengan facturas
                List<ServicioContratado> serviciosAFacturar = servicioContratadoService.obtenerServiciosAFacturar(
                                serviciosActuales,
                                facturasDelMes);
                // generar facturas masivas
                // obtener el empleado autenticado y asignarlo a la factura

                // 1. Obtener el username del usuario autenticado
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                String username = auth.getName();

                // 2. Buscar el usuario en la BD
                Usuario usuario = usuarioRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

                // 3. Obtener el empleado relacionado
                Empleado empleado = usuario.getEmpleado();

                if (serviciosAFacturar.isEmpty()) {
                        redirectAttributes.addFlashAttribute("info", "No  hay servicios para facturar ");
                        return "redirect:/facturas/";
                }
                try {
                        facturaService.generarFacturasMasivas(serviciosAFacturar, empleado);
                } catch (IllegalStateException e) {

                        redirectAttributes.addFlashAttribute("error", e.getMessage());
                        return "redirect:/facturas/";

                }
                return "redirect:/facturas/";
        }

}
