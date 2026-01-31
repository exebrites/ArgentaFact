package com.argentafact.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.argentafact.model.*;
import com.argentafact.repository.CuentaRepository;
import com.argentafact.repository.FacturaRespository;

@Service
@Transactional
public class FacturaService {

    private final FacturaRespository facturaRespository;
    private final CuentaRepository cuentaRepository;

    public FacturaService(FacturaRespository facturaRespository,
            CuentaRepository cuentaRepository) {
        this.facturaRespository = facturaRespository;
        this.cuentaRepository = cuentaRepository;
    }

    public List<Factura> obtenerFacturas() {
        return facturaRespository.findAll();
    }

    public Factura guardarFactura(Factura factura) {

        if (factura.getSaldoPendiente() == null) {
            factura.setSaldoPendiente(factura.getTotal());
        }

        if (factura.getEstado() == null) {
            factura.setEstado(EstadoFactura.PENDIENTE);
        }

        Factura facturaGuardada = facturaRespository.save(factura);

        Cuenta cuenta = cuentaRepository
                .findByClienteIdCliente(factura.getCliente().getIdCliente())
                .orElseThrow(() -> new IllegalStateException("El cliente no posee cuenta"));

        cuenta.acreditar(factura.getTotal());
        cuentaRepository.save(cuenta);

        return facturaGuardada;
    }

    public BigDecimal calcularSaldoPendienteCliente(Long idCliente) {
        return facturaRespository.findAll()
                .stream()
                .filter(Factura::estaActiva)
                .filter(Factura::tieneSaldoPendiente)
                .filter(f -> f.getCliente().getIdCliente().equals(idCliente))
                .map(Factura::getSaldoPendiente)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Factura obtenerFactura(Long id) {
        return facturaRespository.findByIdFactura(id);
    }

    public List<Factura> obtenerFacturasActivas() {
        return facturaRespository.findByBajaFalse();
    }

    public Page<Factura> buscarTodos(PageRequest of) {
        return facturaRespository.findAll(of);
    }

    private Long obtenerIdUltimaFactura() {
        Factura ultimaFactura = facturaRespository.findTopByOrderByIdFacturaDesc();
        return ultimaFactura != null ? ultimaFactura.getIdFactura() : null;
    }

    public String generarNumeroFactura() {
        var puntoVenta = new Empresa().getPuntoVenta();
        Long idUltimaFactura = this.obtenerIdUltimaFactura();

        int numero = (idUltimaFactura == null) ? 1 : idUltimaFactura.intValue() + 1;

        return String.format("%04d-%08d", puntoVenta, numero);
    }

    public List<Factura> obtenerFacturasConSaldoPendiente() {
        return facturaRespository.findAll()
                .stream()
                .filter(Factura::tieneSaldoPendiente)
                .filter(Factura::estaActiva)
                .collect(Collectors.toList());
    }

    public List<Factura> obtenerFacturasDelMesActual() {

        var facturas = this.obtenerFacturas();
        LocalDate mesActual = LocalDate.now();
        List<Factura> facturaActuales = new ArrayList<>();
        for (var factura : facturas) {
            if (factura.getFechaEmision().getMonth() == mesActual.getMonth()) {
                facturaActuales.add(factura);
            }
        }
        return facturaActuales;
    }

    public void generarFacturasMasivas(List<ServicioContratado> serviciosAFacturar, List<Empleado> empleados) {
        
        // obtener los clientes únicos de los servicios a facturar
        List<Cliente> clientes = new ArrayList<>();
        for (ServicioContratado servicioContratado : serviciosAFacturar) {
            if (!clientes.contains(servicioContratado.getCliente())) {
                clientes.add(servicioContratado.getCliente());
            }
        }

        // generar facturas para cada cliente
        for (Cliente cliente : clientes) {
            // crear factura
            Factura factura = new Factura();
            factura.setCliente(cliente);
            factura.setEmpleado(empleados.get(0));
            String nroFactura = this.generarNumeroFactura();
            factura.setNumeroFactura(nroFactura);
            factura.setEstado(EstadoFactura.PENDIENTE);
            factura.setFechaEmision(LocalDate.now());
            BigDecimal totalServicos = BigDecimal.ZERO;

            // // // determinar el tipo de factura segun la condicion fiscal del cliente.
            if ((cliente.getCondicionFiscal().equals(CondicionFiscal.RESPONSABLE_INSCRIPTO))
                    ||
                    (factura.getCliente().getCondicionFiscal().equals(CondicionFiscal.MONOTRIBUTISTA))) {
                factura.setTipoFactura(TipoFactura.A);
            } else {
                factura.setTipoFactura(TipoFactura.B);
            }
            // crear detalle de factura
            for (ServicioContratado servicioContratado : serviciosAFacturar) {
                if (cliente.getIdCliente() != servicioContratado.getCliente().getIdCliente()) {
                    continue;
                }

                var detalleFactura = new DetalleFactura(factura,
                        servicioContratado.getServicio(),
                        servicioContratado.getPrecioAcordado());

                factura.AgregarDetalle(detalleFactura);
                totalServicos = totalServicos.add(servicioContratado.getPrecioAcordado());
            }
            factura.setTotal(totalServicos);
            this.guardarFactura(factura);
        }


    }
}
