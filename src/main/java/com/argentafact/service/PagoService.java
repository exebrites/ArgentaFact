package com.argentafact.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.argentafact.model.*;
import com.argentafact.repository.FacturaRespository;
import com.argentafact.repository.HistoricoFiscalRepository;
import com.argentafact.repository.PagoRepository;
import com.argentafact.repository.CuentaRepository;

@Service
@Transactional
public class PagoService {

    private final PagoRepository pagoRepository;
    private final FacturaRespository facturaRespository;
    private final CuentaRepository cuentaRepository;
    private final HistoricoFiscalRepository historicoFiscalRepository;

    public PagoService(PagoRepository pagoRepository,
            FacturaRespository facturaRespository,
            CuentaRepository cuentaRepository,
            HistoricoFiscalRepository historicoFiscalRepository) {
        this.pagoRepository = pagoRepository;
        this.facturaRespository = facturaRespository;
        this.cuentaRepository = cuentaRepository;
        this.historicoFiscalRepository = historicoFiscalRepository;
    }

    public void registrarPago(Long idFactura,
            BigDecimal monto,
            TipoPago tipoPago,
            Empleado empleado) {

        Factura factura = facturaRespository.findById(idFactura)
                .orElseThrow(() -> new IllegalArgumentException("Factura inexistente"));

        if (factura.getEstado() == EstadoFactura.ANULADA) {
            throw new IllegalStateException("No se puede pagar una factura anulada");
        }

        Pago pago = new Pago(
                monto,
                tipoPago,
                factura,
                empleado);
        pagoRepository.save(pago);

        factura.aplicarPago(monto);
        facturaRespository.save(factura);

        Long idCliente = factura.getCliente().getIdCliente();

        Cuenta cuenta = cuentaRepository.findByClienteIdCliente(idCliente)
                .orElseThrow(() -> new IllegalStateException("El cliente no posee cuenta"));

        cuenta.debitar(monto);
        cuentaRepository.save(cuenta);

        HistoricoFiscal historico = new HistoricoFiscal(
                LocalDateTime.now(),
                empleado,
                factura.getCliente(),
                "REGISTRO_PAGO",
                "Pago registrado sobre la factura N° "
                        + factura.getNumeroFactura()
                        + " por un monto de $"
                        + monto);

        historicoFiscalRepository.save(historico);
    }

    public List<Pago> listarPagos() {
        return pagoRepository.findAll();
    }

    public Page<Pago> listarPagos(PageRequest of) {
        return pagoRepository.findAll(of);
    }

    public BigDecimal obtenerTotalPagosDelMesActual() {

        var pagos = pagoRepository.findAll();

        LocalDate fechaActual = LocalDate.now();
        List<Pago> pagosDelMes = new ArrayList<>();
        for (Pago pago : pagos) {
            if (fechaActual.getMonth() == pago.getFecha().getMonth()) {
                pagosDelMes.add(pago);
            }
        }

        BigDecimal totalPagosMes = BigDecimal.ZERO;
        for (Pago pago : pagosDelMes) {
            totalPagosMes = totalPagosMes.add(pago.getMonto());
        }
        return totalPagosMes;
    }
}
