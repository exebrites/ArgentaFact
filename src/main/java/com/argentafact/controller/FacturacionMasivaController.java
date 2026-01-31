package com.argentafact.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.argentafact.model.Cliente;
import com.argentafact.model.CondicionFiscal;
import com.argentafact.model.DetalleFactura;
import com.argentafact.model.Empleado;
import com.argentafact.model.EstadoFactura;
import com.argentafact.model.EstadoServicioContratado;
import com.argentafact.model.Factura;
import com.argentafact.model.ServicioContratado;
import com.argentafact.model.TipoFactura;
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

    @GetMapping("/")
    public String facturarMasivo() {
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
        List<Empleado> empleados = empleadoService.buscarTodos();
        facturaService.generarFacturasMasivas(serviciosAFacturar, empleados);
        

        return "redirect:/facturas/";
    }

}
