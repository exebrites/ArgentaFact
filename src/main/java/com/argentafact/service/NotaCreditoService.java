package com.argentafact.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.argentafact.model.Cuenta;
import com.argentafact.model.EstadoFactura;
import com.argentafact.model.Factura;
import com.argentafact.model.NotaCredito;
import com.argentafact.repository.NotaCreditoRepository;

@Service
public class NotaCreditoService {

    private final NotaCreditoRepository notaCreditoRepository;
    private final FacturaService facturaService;
    private final CuentaService cuentaService;

    public NotaCreditoService(NotaCreditoRepository notaCreditoRepository,
                              FacturaService facturaService,
                              CuentaService cuentaService) {
        this.notaCreditoRepository = notaCreditoRepository;
        this.facturaService = facturaService;
        this.cuentaService = cuentaService;
    }

    @Transactional
    public void guardarNotaCredito(NotaCredito notaCredito) {
        Factura factura = notaCredito.getFactura();

        if (!factura.estaActiva() || !factura.tieneSaldoPendiente()) {
            throw new RuntimeException("La factura no se puede afectar o ya está pagada completamente");
        }

        if (notaCredito.getMonto() == null) {
            notaCredito.setMonto(factura.getSaldoPendiente());
        } else if (notaCredito.getMonto().compareTo(factura.getSaldoPendiente()) > 0) {
            throw new RuntimeException("El monto de la nota de crédito no puede superar el saldo pendiente");
        }

        BigDecimal monto = notaCredito.getMonto();

        factura.aplicarPago(monto);

        if (factura.getSaldoPendiente().compareTo(BigDecimal.ZERO) <= 0) {
            factura.setEstado(EstadoFactura.ANULADA);
        }

        facturaService.actualizarFactura(factura);

        Cuenta cuenta = cuentaService.obtenerCuentaPorCliente(factura.getCliente().getIdCliente());
        cuentaService.debitar(cuenta.getIdCuenta(), monto);

        notaCreditoRepository.save(notaCredito);
    }

    public List<NotaCredito> buscarTodas() {
        return notaCreditoRepository.findAll();
    }

    public NotaCredito obtenerNotaCredito(Long id) {
        return notaCreditoRepository.findByIdNotaCredito(id);
    }

    public Page<NotaCredito> buscarTodos(PageRequest of) {
        return notaCreditoRepository.findAll(of);
    }
}