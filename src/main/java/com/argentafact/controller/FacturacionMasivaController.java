package com.argentafact.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.argentafact.model.DetalleFactura;
import com.argentafact.model.EstadoServicioContratado;
import com.argentafact.model.Factura;
import com.argentafact.model.ServicioContratado;
import com.argentafact.service.ClienteService;
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

    @GetMapping("/")
    public String facturarMasivo() {
        // TODO obtener todos los contratos activos del mes actual
        // var serviciosActuales = servicioContratadoService
        // .obtenerServiciosContratadosActivosDelMesActual();
        var serviciosActuales = servicioContratadoService.obtenerServiciosContratadosActivosDelMesActual();

        // TODO verificar si tienen que no tengan facturas en el mes actual
        // todas las facturas de este mes
        var facturas = facturaService.obtenerFacturas();
        LocalDate mesActual = LocalDate.now();
        List<Factura> facturaActuales = new ArrayList<>();
        for (var factura : facturas) {
            if (factura.getFechaEmision().getMonth() == mesActual.getMonth()) {
                facturaActuales.add(factura);
            }
        }
        // filtrar los servicios que no tengan facturas en el mes actual
        // iterar sobre facturas del mes
        // iterar sobre servicioContratoMes
        // si el cliente de la factura coincide con el cliente del servicio continuar
        // iterar sobre detalles de factura
        // obtener el id del servicio en el detalle de factura y comparar con el
        // servicio contratado
        // si el id del servicio contratado no esta en los detalles de factura, facturar
        // ese servicio
        // List<ServicioContratado> serviciosAFacturar = new ArrayList<>();
        System.out.println("Facturas del mes actual:");
        for (Factura factura : facturaActuales) {

            System.out.println("Factura: " + factura);
        }
        // for (ServicioContratado servicioContrato : serviciosActuales) {
        // if (factura.getCliente().getIdCliente() ==
        // servicioContrato.getCliente().getIdCliente()) {
        // boolean encontrado = false;
        // for (DetalleFactura detalle : factura.getDetalleFacturas()) {
        // if (detalle.getServicio().getIdServicio() ==
        // servicioContrato.getServicio().getIdServicio()) {
        // encontrado = true;
        // break;
        // }
        // }
        // if (!encontrado) {
        // // serviciosAFacturar.add(servicioContrato);
        // // TODO generar factura para este servicio
        // System.out.println("Generando factura para el servicio: " +
        // servicioContrato.getServicio().getNombreServicio());
        // }
        // }
        // }

        // TODO obtener datos del cliente
        // TODO obtener los servicios
        // TODO generar facturas

        return "redirect:/facturas/";
    }

}
