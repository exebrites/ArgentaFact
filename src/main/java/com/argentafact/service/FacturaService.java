package com.argentafact.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.argentafact.model.Empresa;
import com.argentafact.model.Factura;
import com.argentafact.repository.FacturaRespository;

@Service
public class FacturaService {
    private final FacturaRespository facturaRespository;

    public FacturaService(FacturaRespository facturaRespository) {
        this.facturaRespository = facturaRespository;
    }

    public List<Factura> obtenerFacturas() {
        return facturaRespository.findAll();
    }

    public void guardarFactura(Factura factura) {
        facturaRespository.save(factura);
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

        // Verificar si hay facturas en la base de datos
        if (ultimaFactura == null) {
            return null; // o return 0L; según prefieras
        }

        return ultimaFactura.getIdFactura();
    }

    public String generarNumeroFactura() {
        // generar numero de factura secuencialmente
        var puntoVenta = new Empresa().getPuntoVenta();

        // Obtener el identificador de la ultima factura creada
        Long idUltimaFactura = this.obtenerIdUltimaFactura();

        // Manejar el caso cuando no hay facturas
        int numero;
        if (idUltimaFactura == null) {
            numero = 1; // Primera factura
        } else {
            numero = idUltimaFactura.intValue() + 1;
        }

        return String.format("%04d-%08d", puntoVenta, numero);
    }
}
