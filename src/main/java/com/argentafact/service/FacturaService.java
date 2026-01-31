package com.argentafact.service;

import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;

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
}
