package com.argentafact.service;

import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.argentafact.model.Cuenta;
import com.argentafact.model.EstadoFactura;
import com.argentafact.model.Factura;
import com.argentafact.model.NotaDebito;
import com.argentafact.model.HistoricoFiscal;
import com.argentafact.repository.HistoricoFiscalRepository;
import com.argentafact.repository.FacturaRespository;
import com.argentafact.repository.NotaDebitoRepository;

@Service
public class NotaDebitoService {

    private final FacturaRespository facturaRespository;

    private final NotaDebitoRepository notaDebitoRepository;
    private final HistoricoFiscalRepository historicoFiscalRepository;
    private final CuentaService cuentaService;

    public NotaDebitoService(NotaDebitoRepository notaDebitoRepository,
                             HistoricoFiscalRepository historicoFiscalRepository,
                             CuentaService cuentaService, 
                             FacturaRespository facturaRespository) {
        this.notaDebitoRepository = notaDebitoRepository;
        this.historicoFiscalRepository = historicoFiscalRepository;
        this.cuentaService = cuentaService;
        this.facturaRespository = facturaRespository;
    }

   @Transactional
    public void guardarNotaDebito(NotaDebito notaDebito) {

        Factura factura = facturaRespository.findById(notaDebito.getFactura().getIdFactura())
                .orElseThrow(() -> new RuntimeException("Factura no encontrada"));

        if (!factura.estaActiva()) {
            throw new RuntimeException("La factura no está activa");
        }

        if (notaDebito.getMonto() == null || notaDebito.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Debe indicar un monto válido para la nota de débito");
        }

        BigDecimal monto = notaDebito.getMonto();

        factura.setSaldoPendiente(factura.getSaldoPendiente().add(monto));

        if (factura.getEstado() == EstadoFactura.PAGADA) {
            factura.setEstado(EstadoFactura.PARCIALMENTE_PAGADA);
        } else {
            factura.setEstado(EstadoFactura.PENDIENTE);
        }

        facturaRespository.save(factura); 

        Cuenta cuenta = cuentaService.obtenerCuentaPorCliente(factura.getCliente().getIdCliente());
        cuentaService.acreditar(cuenta.getIdCuenta(), monto);

        notaDebitoRepository.save(notaDebito);

        HistoricoFiscal historico = new HistoricoFiscal(
                LocalDateTime.now(),
                factura.getEmpleado(),
                factura.getCliente(),
                "NOTA_DEBITO",
                "Nota de débito sobre factura N° "
                        + factura.getNumeroFactura()
                        + " por $" + monto);

        historicoFiscalRepository.save(historico);
    }

    public List<NotaDebito> buscarTodas() {
        return notaDebitoRepository.findAll();
    }

    public NotaDebito obtenerNotaDebito(Long id) {
        return notaDebitoRepository.findByIdNotaDebito(id);
    }

    public Page<NotaDebito> buscarTodos(PageRequest of) {
        return notaDebitoRepository.findAll(of);
    }
}
