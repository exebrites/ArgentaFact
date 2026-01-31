package com.argentafact.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.argentafact.model.Empresa;
import com.argentafact.model.EstadoFactura;
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

    public Factura guardarFactura(Factura factura) {
        if (factura.getSaldoPendiente() == null) {
            factura.setSaldoPendiente(factura.getTotal());
        }

        if (factura.getEstado() == null) {
            factura.setEstado(EstadoFactura.PENDIENTE);
        }

        return facturaRespository.save(factura);
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

    public Object obtenerFacturasDelMesActual() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerFacturasDelMesActual'");
    }
}
