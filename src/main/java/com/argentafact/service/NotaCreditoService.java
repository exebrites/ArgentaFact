package com.argentafact.service;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.argentafact.controller.Motivo;
import com.argentafact.model.Cuenta;
import com.argentafact.model.EstadoFactura;
import com.argentafact.model.Factura;
import com.argentafact.model.NotaCredito;
import com.argentafact.model.HistoricoFiscal;
import com.argentafact.repository.HistoricoFiscalRepository;
import com.argentafact.repository.NotaCreditoRepository;

@Service
public class NotaCreditoService {

    private final NotaCreditoRepository notaCreditoRepository;
    private final FacturaService facturaService;
    private final CuentaService cuentaService;
    private final HistoricoFiscalRepository historicoFiscalRepository;

    public NotaCreditoService(NotaCreditoRepository notaCreditoRepository,
            FacturaService facturaService,
            CuentaService cuentaService,
            HistoricoFiscalRepository historicoFiscalRepository) {
        this.notaCreditoRepository = notaCreditoRepository;
        this.facturaService = facturaService;
        this.cuentaService = cuentaService;
        this.historicoFiscalRepository = historicoFiscalRepository;
    }

    @Transactional
    public void guardarNotaCredito(NotaCredito notaCredito) throws RuntimeException {
        Factura factura = notaCredito.getFactura();
        notaCredito.setMotivo(Motivo.DISMINUIR);
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
            notaCredito.setMotivo(Motivo.CANCELACION);
            factura.setEstado(EstadoFactura.ANULADA);
            factura.setBaja(true);
        }

        facturaService.actualizarFactura(factura);

        Cuenta cuenta = cuentaService.obtenerCuentaPorCliente(factura.getCliente().getIdCliente());
        cuentaService.debitar(cuenta.getIdCuenta(), monto);

        notaCreditoRepository.save(notaCredito);

        HistoricoFiscal historico = new HistoricoFiscal(
                LocalDateTime.now(),
                factura.getEmpleado(),
                factura.getCliente(),
                "NOTA_CREDITO",
                "Nota de crédito sobre factura N° "
                        + factura.getNumeroFactura()
                        + " por $" + monto);

        historicoFiscalRepository.save(historico);

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